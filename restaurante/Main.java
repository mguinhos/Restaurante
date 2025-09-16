package restaurante;
import restaurante.objetos.Alimento;
import restaurante.objetos.Cliente;
import restaurante.objetos.Funcionario;
import java.util.Vector;
import java.util.Scanner;
import java.util.function.Consumer;

enum TipoEvento {
    KEY_UP, KEY_DOWN, ENTER, ESC
}

class EventoTeclado {
    TipoEvento tipo;
    
    EventoTeclado(TipoEvento tipo) {
        this.tipo = tipo;
    }
}

interface Focusable {
    void onFocus();
    void onBlur();
    boolean isFocused();
}

interface Drawable {
    int getWidth();
    int getHeight();
    String[] renderLines();
}

abstract class Widget implements Drawable {
    Vector<Widget> children;
    Widget parent;
    protected int width;
    protected int height;
    protected int paddingTop = 0;
    protected int paddingBottom = 0;
    protected int paddingLeft = 1;
    protected int paddingRight = 1;
    
    Widget() {
        this.children = new Vector<Widget>();
        this.parent = null;
        this.width = 0;
        this.height = 0;
    }
    
    Widget addWidget(Widget widget) {
        widget.parent = this;
        this.children.add(widget);
        return this;
    }
    
    abstract String exibir();
    
    Widget processarEvento(EventoTeclado evento) {
        return this;
    }
    
    Widget encontrarProximoFocavel() {
        if (parent != null) {
            return parent.encontrarProximoFocavel();
        }
        return null;
    }
    
    Widget encontrarAnteriorFocavel() {
        if (parent != null) {
            return parent.encontrarAnteriorFocavel();
        }
        return null;
    }
    
    protected void calculateDimensions() {
        
    }
    
    protected String[] drawBox(String[] content) {
        if (content == null || content.length == 0) {
            return new String[]{"┌┐", "└┘"};
        }
        
        int maxWidth = 0;
        for (String line : content) {
            maxWidth = Math.max(maxWidth, line.length());
        }
        
        String[] result = new String[content.length + 2];
        
        
        result[0] = "┌" + "─".repeat(maxWidth + paddingLeft + paddingRight) + "┐";
        
        
        for (int i = 0; i < content.length; i++) {
            String line = content[i];
            String padding = " ".repeat(Math.max(0, maxWidth - line.length()));
            result[i + 1] = "│" + " ".repeat(paddingLeft) + line + padding + " ".repeat(paddingRight) + "│";
        }
        
        
        result[result.length - 1] = "└" + "─".repeat(maxWidth + paddingLeft + paddingRight) + "┘";
        
        return result;
    }
    
    @Override
    public int getWidth() {
        return width;
    }
    
    @Override
    public int getHeight() {
        return height;
    }
    
    @Override
    public String[] renderLines() {
        String[] content = {exibir()};
        return drawBox(content);
    }
}

class Label extends Widget {
    private String texto;
    
    Label(String texto) {
        this.texto = texto;
        this.width = texto.length();
        this.height = 1;
    }
    
    @Override
    String exibir() {
        return this.texto;
    }
    
    @Override
    public String[] renderLines() {
        
        return new String[]{texto};
    }
    
    public void setText(String texto) {
        this.texto = texto;
        this.width = texto.length();
    }
}

class Opcao extends Widget implements Focusable {
    String texto;
    Consumer<Opcao> funcao;
    private boolean focused = false;
    private UI uiRef;
    
    Opcao(String texto, Consumer<Opcao> funcao) {
        this.texto = texto;
        this.funcao = funcao;
        this.width = texto.length() + 4; 
        this.height = 1;
    }
    
    public void setUIReference(UI ui) {
        this.uiRef = ui;
    }
    
    @Override
    Widget processarEvento(EventoTeclado evento) {
        if (focused && evento.tipo == TipoEvento.ENTER) {
            this.funcao.accept(this);
            
            if (uiRef != null) {
                uiRef.forceRedraw();
            }
        }
        return this;
    }
    
    @Override
    String exibir() {
        if (focused) {
            return "> " + this.texto + " <";
        }
        return "  " + this.texto + "  ";
    }
    
    @Override
    public void onFocus() {
        this.focused = true;
    }
    
    @Override
    public void onBlur() {
        this.focused = false;
    }
    
    @Override
    public boolean isFocused() {
        return this.focused;
    }
}

class Menu extends Widget {
    private int selectedIndex = 0;
    private String titulo;
    
    Menu() {
        this("Menu");
    }
    
    Menu(String titulo) {
        this.titulo = titulo;
    }
    
    @Override
    Widget addWidget(Widget widget) {
        super.addWidget(widget);
        calculateDimensions();
        return this;
    }
    
    @Override
    protected void calculateDimensions() {
        int maxWidth = titulo.length();
        int totalHeight = 0;
        
        for (Widget child : children) {
            maxWidth = Math.max(maxWidth, child.getWidth());
            totalHeight += child.getHeight();
        }
        
        this.width = maxWidth + paddingLeft + paddingRight + 2; 
        this.height = totalHeight + paddingTop + paddingBottom + 3; 
    }
    
    @Override
    String exibir() {
        StringBuilder output = new StringBuilder();
        
        if (!titulo.isEmpty()) {
            output.append(titulo).append("\n");
            output.append("─".repeat(titulo.length())).append("\n");
        }
        
        for (int i = 0; i < this.children.size(); i++) {
            Widget child = this.children.get(i);
            if (i == selectedIndex && child instanceof Focusable) {
                ((Focusable) child).onFocus();
            } else if (child instanceof Focusable) {
                ((Focusable) child).onBlur();
            }
            output.append(child.exibir());
            if (i < children.size() - 1) {
                output.append("\n");
            }
        }
        
        return output.toString();
    }
    
    @Override
    public String[] renderLines() {
        Vector<String> lines = new Vector<>();
        
        if (!titulo.isEmpty()) {
            lines.add(titulo);
            lines.add("─".repeat(titulo.length()));
        }
        
        for (int i = 0; i < this.children.size(); i++) {
            Widget child = this.children.get(i);
            if (i == selectedIndex && child instanceof Focusable) {
                ((Focusable) child).onFocus();
            } else if (child instanceof Focusable) {
                ((Focusable) child).onBlur();
            }
            
            if (child instanceof Label) {
                
                String[] childLines = child.renderLines();
                for (String line : childLines) {
                    lines.add(line);
                }
            } else {
                
                lines.add(child.exibir());
            }
        }
        
        String[] content = lines.toArray(new String[0]);
        return drawBox(content);
    }
    
    @Override
    Widget processarEvento(EventoTeclado evento) {
        switch (evento.tipo) {
            case KEY_UP:
                selectedIndex = (selectedIndex - 1 + children.size()) % children.size();
                break;
            case KEY_DOWN:
                selectedIndex = (selectedIndex + 1) % children.size();
                break;
            case ENTER:
                if (selectedIndex < children.size()) {
                    children.get(selectedIndex).processarEvento(evento);
                }
                break;
            case ESC:
                break;
        }
        return this;
    }
    
    public int getSelectedIndex() {
        return selectedIndex;
    }
}

class UI extends Widget {
    private Widget widgetAtivo;
    private boolean needsRedraw = true;
    
    public UI() {
        super();
        this.widgetAtivo = null;
        this.paddingTop = 1;
        this.paddingBottom = 1;
    }
    
    @Override
    Widget addWidget(Widget widget) {
        super.addWidget(widget);
        if (widgetAtivo == null) {
            widgetAtivo = widget;
        }
        
        
        if (widget instanceof Menu) {
            setUIReferenceRecursive(widget);
        }
        
        return this;
    }
    
    private void setUIReferenceRecursive(Widget widget) {
        if (widget instanceof Opcao) {
            ((Opcao) widget).setUIReference(this);
        }
        for (Widget child : widget.children) {
            setUIReferenceRecursive(child);
        }
    }
    
    public void forceRedraw() {
        this.needsRedraw = true;
    }
    
    @Override
    String exibir() {
        StringBuilder output = new StringBuilder();
        output.append("=== RESTAURANTE - SISTEMA DE GESTÃO ===\n");
        output.append("Controles: ↑/↓ para navegar, ENTER para selecionar, 'q' para sair\n");
        output.append("==========================================\n\n");
        
        for (Widget child : children) {
            String[] lines = child.renderLines();
            for (String line : lines) {
                output.append(line).append("\n");
            }
            output.append("\n");
        }
        
        return output.toString();
    }
    
    @Override
    public String[] renderLines() {
        Vector<String> allLines = new Vector<>();
        
        allLines.add("=== RESTAURANTE - SISTEMA DE GESTÃO =============================");
        allLines.add("Controles: ↑/↓ para navegar, ENTER para selecionar, 'q' para sair");
        allLines.add("=================================================================");
        allLines.add("");
        
        for (Widget child : children) {
            String[] childLines = child.renderLines();
            for (String line : childLines) {
                allLines.add(line);
            }
            allLines.add(""); 
        }
        
        return allLines.toArray(new String[0]);
    }
    
    @Override
    Widget processarEvento(EventoTeclado evento) {
        if (widgetAtivo != null) {
            widgetAtivo.processarEvento(evento);
        }
        return this;
    }
    
    public void setWidgetAtivo(Widget widget) {
        this.widgetAtivo = widget;
    }
    
    public boolean needsRedraw() {
        return needsRedraw;
    }
    
    public void setRedrawComplete() {
        this.needsRedraw = false;
    }
}

class TecladoHandler {
    private Scanner scanner;
    
    public TecladoHandler() {
        this.scanner = new Scanner(System.in);
    }
    
    public EventoTeclado lerProximoEvento() {
        System.out.print("Digite comando (w=cima, s=baixo, enter=selecionar, q=sair): ");
        String input = scanner.nextLine().toLowerCase().trim();
        
        switch (input) {
            case "w":
            case "up":
                return new EventoTeclado(TipoEvento.KEY_UP);
            case "s":
            case "down":
                return new EventoTeclado(TipoEvento.KEY_DOWN);
            case "":
            case "enter":
                return new EventoTeclado(TipoEvento.ENTER);
            case "q":
            case "quit":
            case "esc":
                return new EventoTeclado(TipoEvento.ESC);
            default:
                System.out.println("Comando inválido!");
                return lerProximoEvento();
        }
    }
    
    public void fechar() {
        scanner.close();
    }
}

public class Main {
    static UI ui = new UI();
    
    public static void limparTela() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else if (System.getProperty("os.name").contains("Linux")) {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
            else {
                System.out.print("\033[2J\033[H");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
    
    public static void main(String[] args) {
        Restaurante restaurante = new Restaurante();
        restaurante.Abrir();
        
        Menu menuPrincipal = new Menu("Menu Principal");
        
        menuPrincipal.addWidget(new Label("Bem-vindo ao Sistema do Restaurante!"));
        menuPrincipal.addWidget(new Label(""));
        
        menuPrincipal
            .addWidget(new Opcao("Mostrar Cardápio do dia", (opcao) -> {
                System.out.println("\n=== EXECUTANDO: Mostrar Cardápio ===");
                restaurante.ListaAlimentos();
                System.out.println("Pressione ENTER para continuar...");
                new Scanner(System.in).nextLine();
            }))
            .addWidget(new Opcao("Listar Funcionários", (opcao) -> {
                System.out.println("\n=== EXECUTANDO: Listar Funcionários ===");
                restaurante.ListaFuncionarios();
                System.out.println("Pressione ENTER para continuar...");
                new Scanner(System.in).nextLine();
            }))
            .addWidget(new Opcao("Sair do Sistema", (opcao) -> {
                System.out.println("\n=== SAINDO DO SISTEMA ===");
                System.exit(0);
            }));
        
        ui.addWidget(menuPrincipal);
        ui.setWidgetAtivo(menuPrincipal);
        
        TecladoHandler teclado = new TecladoHandler();
        
        try {
            while (true) {
                if (ui.needsRedraw()) {
                    limparTela();
                    String[] lines = ui.renderLines();
                    for (String line : lines) {
                        System.out.println(line);
                    }
                    ui.setRedrawComplete();
                }
                
                EventoTeclado evento = teclado.lerProximoEvento();
                
                if (evento.tipo == TipoEvento.ESC) {
                    System.out.println("Encerrando aplicação...");
                    break;
                }
                
                ui.processarEvento(evento);
                ui.forceRedraw(); 
            }
        } finally {
            teclado.fechar();
        }
    }
}