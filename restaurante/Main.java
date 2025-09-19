package restaurante;

import restaurante.acao.Acao;
import restaurante.alimento.BatatasFritas;
import restaurante.alimento.Hamburguer;
import restaurante.alimento.Refrigerante;
import restaurante.pessoa.Funcionario;
import restaurante.ingrediente.Ingrediente;

import java.util.Vector;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.ArrayList;

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
            return new String[] { "┌┐", "└┘" };
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
        String[] content = { exibir() };
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
        return new String[] { texto };
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

class InfoBox extends Widget {
    private String titulo;
    private Vector<String> linhas;

    InfoBox(String titulo) {
        this.titulo = titulo;
        this.linhas = new Vector<>();
    }

    public void adicionarLinha(String linha) {
        this.linhas.add(linha);
    }

    public void limpar() {
        this.linhas.clear();
    }

    @Override
    String exibir() {
        StringBuilder output = new StringBuilder();

        if (!titulo.isEmpty()) {
            output.append(titulo).append("\n");
            output.append("─".repeat(titulo.length())).append("\n");
        }

        for (String linha : linhas) {
            output.append(linha).append("\n");
        }

        return output.toString();
    }

    @Override
    public String[] renderLines() {
        Vector<String> allLines = new Vector<>();

        if (!titulo.isEmpty()) {
            allLines.add(titulo);
            allLines.add("─".repeat(titulo.length()));
        }

        for (String linha : linhas) {
            allLines.add(linha);
        }

        return drawBox(allLines.toArray(new String[0]));
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
        // Se não houver widget ativo, define o que acabou de ser adicionado
        if (widgetAtivo == null) {
            widgetAtivo = widget;
        }
        // Se o widget adicionado for um Menu, garantir que ele receba foco (para navegação)
        if (widget instanceof Menu) {
            setWidgetAtivo(widget);
            setUIReferenceRecursive(widget);
        } else {
            // garantir referência para Opcao caso um Menu esteja dentro da hierarquia
            if (widget instanceof Opcao) {
                setUIReferenceRecursive(this); // assegura referências (fallback)
            }
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
        output.append("=== PAINEL DO FRANQUEADO ===\n");
        output.append("Controles: W/S=navegar, ENTER=selecionar, Q=sair\n");
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

        allLines.add("════════════════════ PAINEL DO FRANQUEADO ═══════════════════════");
        allLines.add("Controles: ↑/↓ navegar, ENTER selecionar, 'q' sair");
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

    public void limparTudo() {
        this.children.clear();
    }
}

class TecladoHandler {
    private Scanner scanner;

    public TecladoHandler() {
        this.scanner = new Scanner(System.in);
    }

    public EventoTeclado lerProximoEvento() {
        System.out.print("Comando [W=↑ S=↓ ENTER=✓ Q=✕]: ");
        String input = scanner.nextLine().toLowerCase().trim();

        switch (input) {
            case "w":
                return new EventoTeclado(TipoEvento.KEY_UP);
            case "s":
                return new EventoTeclado(TipoEvento.KEY_DOWN);
            case "":
                return new EventoTeclado(TipoEvento.ENTER);
            case "q":
                return new EventoTeclado(TipoEvento.ESC);
            default:
                return lerProximoEvento(); // Tentar novamente silenciosamente
        }
    }

    public void fechar() {
        scanner.close();
    }
}

public class Main {
    static UI ui = new UI();
    static Restaurante restaurante;
    static Sistema sistema;
    static Scanner scanner = new Scanner(System.in);

    // Histórico financeiro para calcular rentabilidade
    static ArrayList<Float> historicoReceitas = new ArrayList<>();
    static ArrayList<Float> historicoDespesas = new ArrayList<>();

    public static void limparTela() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else if (System.getProperty("os.name").contains("Linux")) {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[2J\033[H");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    public static float calcularRentabilidadeMedia() {
        if (historicoReceitas.size() == 0)
            return 0.0f;

        float totalReceitas = 0.0f;
        float totalDespesas = 0.0f;

        for (Float receita : historicoReceitas) {
            totalReceitas += receita;
        }

        for (Float despesa : historicoDespesas) {
            totalDespesas += despesa;
        }

        if (historicoReceitas.size() == 0)
            return 0.0f;

        float lucroMedio = (totalReceitas - totalDespesas) / historicoReceitas.size();
        return lucroMedio;
    }

    public static float calcularSalario(Cargo cargo) {
        switch (cargo) {
            case Gerente:
                return 200.0f;
            case Cozinheiro:
                return 150.0f;
            case Caixa:
                return 120.0f;
            case Estoquista:
                return 100.0f;
            case Faxineiro:
                return 80.0f;
            default:
                return 100.0f;
        }
    }

    public static Cargo obterCargoSuperior(Cargo cargoAtual) {
        switch (cargoAtual) {
            case Faxineiro:
                return Cargo.Estoquista;
            case Estoquista:
                return Cargo.Caixa;
            case Caixa:
                return Cargo.Cozinheiro;
            case Cozinheiro:
                return Cargo.Gerente;
            case Gerente:
                return null;
            default:
                return null;
        }
    }

    public static Cargo obterCargoInferior(Cargo cargoAtual) {
        switch (cargoAtual) {
            case Gerente:
                return Cargo.Cozinheiro;
            case Cozinheiro:
                return Cargo.Caixa;
            case Caixa:
                return Cargo.Estoquista;
            case Estoquista:
                return Cargo.Faxineiro;
            case Faxineiro:
                return null;
            default:
                return null;
        }
    }

    public static void mostrarDashboard() {
        InfoBox dashboard = new InfoBox("💼 Dashboard do Franqueado");

        dashboard.adicionarLinha("💰 Cofre: R$ " + String.format("%.2f", restaurante.obterDinheiroNoCofre()));
        dashboard.adicionarLinha(
                "📈 Rentabilidade média (30 dias): R$ " + String.format("%.2f", calcularRentabilidadeMedia()) + "/dia");
        dashboard.adicionarLinha("👥 Total funcionários: " + restaurante.obterFuncionarios().size());
        dashboard.adicionarLinha("🍽️  Itens no cardápio: " + restaurante.obteCardapio().obterItemsNoCardapio().size());
        dashboard.adicionarLinha("📦 Itens esgotados: "
                + restaurante.obteCardapio().obterItemsEsgotados(restaurante.obterEstoque()).size());

        ui.limparTudo();
        ui.addWidget(dashboard);
        Menu menu = criarMenuPrincipal();
        ui.addWidget(menu);
        // garante que o menu receba foco para navegação
        ui.setWidgetAtivo(menu);
    }

    public static Menu criarMenuPrincipal() {
        Menu menuPrincipal = new Menu("🏪 Menu Principal");

        menuPrincipal
                .addWidget(new Opcao("👥 Gerenciar Funcionários", (opcao) -> {
                    mostrarGerenciarFuncionarios();
                }))
                .addWidget(new Opcao("📦 Gerenciar Estoque", (opcao) -> {
                    mostrarGerenciarEstoque();
                }))
                .addWidget(new Opcao("💡 Ver Ações Recomendadas", (opcao) -> {
                    mostrarAcoesRecomendadas();
                }))
                .addWidget(new Opcao("💰 Gerenciar Preços", (opcao) -> {
                    mostrarGerenciarPrecos();
                }))
                .addWidget(new Opcao("💵 Ver Salários", (opcao) -> {
                    mostrarSalarios();
                }))
                .addWidget(new Opcao("📊 Resumo da Simulação", (opcao) -> {
                    mostrarResumoSimulacao();
                }))
                .addWidget(new Opcao("🚪 Sair", (opcao) -> {
                    System.exit(0);
                }));

        return menuPrincipal;
    }

    public static void mostrarGerenciarFuncionarios() {
        ui.limparTudo();

        InfoBox infoFuncionarios = new InfoBox("👥 Funcionários");

        for (Funcionario func : restaurante.obterFuncionarios()) {
            String emoji = func.desempenhoEstaAlto() ? "🟢" : func.desempenhoEstaBaixo() ? "🔴" : "🟡";

            infoFuncionarios.adicionarLinha(emoji + " " + func.obterNome() +
                    " - " + func.obterCargo() +
                    " (Aval: " + String.format("%.2f", func.obterAvaliacao()) + ")");
        }

        Menu menuFuncionarios = new Menu("Ações");
        menuFuncionarios
                .addWidget(new Opcao("⬆️ Promover Funcionário", (opcao) -> {
                    // Implementar promoção
                    mostrarPromoverFuncionario();
                }))
                .addWidget(new Opcao("⬇️ Rebaixar Funcionário", (opcao) -> {
                    // Implementar rebaixamento
                    mostrarRebaixarFuncionario();
                }))
                .addWidget(new Opcao("🚪 Demitir Funcionário", (opcao) -> {
                    // Implementar demissão
                    mostrarDemitirFuncionario();
                }))
                .addWidget(new Opcao("🔙 Voltar", (opcao) -> {
                    mostrarDashboard();
                }));

        ui.addWidget(infoFuncionarios);
        ui.addWidget(menuFuncionarios);
        ui.setWidgetAtivo(menuFuncionarios);
    }

    public static void mostrarPromoverFuncionario() {
        ui.limparTudo();

        InfoBox info = new InfoBox("⬆️ Promover Funcionário");
        info.adicionarLinha("Selecione o funcionário para promover:");

        Menu menuPromocao = new Menu("Funcionários");

        for (int i = 0; i < restaurante.obterFuncionarios().size(); i++) {
            Funcionario func = restaurante.obterFuncionarios().get(i);

            Cargo cargoSuperior = obterCargoSuperior(func.obterCargo());
            String textoOpcao = func.obterNome() + " (" + func.obterCargo() +
                    (cargoSuperior != null ? " → " + cargoSuperior : " - Máximo");

            menuPromocao.addWidget(new Opcao(textoOpcao, (opcao) -> {
                if (cargoSuperior != null) {
                    func.definirCargo(cargoSuperior);
                    func.avaliar(0.8f);
                    mostrarResultadoAcao("✅ " + func.obterNome() + " promovido para " + cargoSuperior + "!");
                } else {
                    mostrarResultadoAcao("❌ " + func.obterNome() + " já está no cargo máximo!");
                }
            }));
        }

        menuPromocao.addWidget(new Opcao("🔙 Voltar", (opcao) -> {
            mostrarGerenciarFuncionarios();
        }));

        ui.addWidget(info);
        ui.addWidget(menuPromocao);
        ui.setWidgetAtivo(menuPromocao);
    }

    public static void mostrarRebaixarFuncionario() {
        ui.limparTudo();

        InfoBox info = new InfoBox("⬇️ Rebaixar Funcionário");
        info.adicionarLinha("Selecione o funcionário para rebaixar:");

        Menu menuRebaixamento = new Menu("Funcionários");

        for (Funcionario func : restaurante.obterFuncionarios()) {
            Cargo cargoInferior = obterCargoInferior(func.obterCargo());
            String textoOpcao = func.obterNome() + " (" + func.obterCargo() +
                    (cargoInferior != null ? " → " + cargoInferior : " - Mínimo");

            menuRebaixamento.addWidget(new Opcao(textoOpcao, (opcao) -> {
                if (cargoInferior != null) {
                    func.definirCargo(cargoInferior);
                    func.avaliar(0.3f);
                    mostrarResultadoAcao("⚠️ " + func.obterNome() + " rebaixado para " + cargoInferior + "!");
                } else {
                    mostrarResultadoAcao("❌ " + func.obterNome() + " já está no cargo mínimo!");
                }
            }));
        }

        menuRebaixamento.addWidget(new Opcao("🔙 Voltar", (opcao) -> {
            mostrarGerenciarFuncionarios();
        }));

        ui.addWidget(info);
        ui.addWidget(menuRebaixamento);
        ui.setWidgetAtivo(menuRebaixamento);
    }

    public static void mostrarDemitirFuncionario() {
        ui.limparTudo();

        InfoBox info = new InfoBox("🚪 Demitir Funcionário");
        info.adicionarLinha("⚠️ ATENÇÃO: Esta ação é irreversível!");
        info.adicionarLinha("Selecione o funcionário para demitir:");

        Menu menuDemissao = new Menu("Funcionários");

        for (Funcionario func : restaurante.obterFuncionarios()) {
            menuDemissao.addWidget(new Opcao("🗑️ " + func.obterNome() + " (" + func.obterCargo() + ")", (opcao) -> {
                // Verificar se é o último gerente
                if (func.obterCargo() == Cargo.Gerente) {
                    long gerentesCount = restaurante.obterFuncionarios().stream()
                            .filter(f -> f.obterCargo() == Cargo.Gerente).count();
                    if (gerentesCount <= 1) {
                        mostrarResultadoAcao("❌ Não é possível demitir o último gerente!");
                        return;
                    }
                }

                restaurante.obterFuncionarios().remove(func);
                mostrarResultadoAcao("✅ " + func.obterNome() + " foi demitido!");
            }));
        }

        menuDemissao.addWidget(new Opcao("🔙 Voltar", (opcao) -> {
            mostrarGerenciarFuncionarios();
        }));

        ui.addWidget(info);
        ui.addWidget(menuDemissao);
        ui.setWidgetAtivo(menuDemissao);
    }

    public static void mostrarGerenciarEstoque() {
        ui.limparTudo();

        InfoBox infoEstoque = new InfoBox("📦 Estoque Atual");
        ArrayList<Ingrediente> ingredientesEstoque = restaurante.obterEstoque().obterIngredientes();

        if (ingredientesEstoque.isEmpty()) {
            infoEstoque.adicionarLinha("❌ Estoque vazio!");
        } else {
            for (Ingrediente ingrediente : ingredientesEstoque) {
                infoEstoque.adicionarLinha("✅ " + ingrediente.obterNome());
            }
        }

        InfoBox infoFaltantes = new InfoBox("📋 Ingredientes Faltantes");
        ArrayList<Ingrediente> ingredientesFaltantes = sistema.obterIngredientesQueFaltam();

        Menu menuEstoque = new Menu("Ações");

        if (ingredientesFaltantes.isEmpty()) {
            infoFaltantes.adicionarLinha("✅ Todos ingredientes disponíveis!");
            menuEstoque.addWidget(new Opcao("🔙 Voltar", (opcao) -> {
                mostrarDashboard();
            }));
        } else {
            for (Ingrediente ingrediente : ingredientesFaltantes) {
                infoFaltantes.adicionarLinha("❌ " + ingrediente.obterNome());
            }

            for (Ingrediente ingrediente : ingredientesFaltantes) {
                menuEstoque.addWidget(new Opcao("🛒 Repor " + ingrediente.obterNome(), (opcao) -> {
                    restaurante.obterEstoque().adicionarIngrediente(ingrediente);
                    mostrarResultadoAcao("✅ " + ingrediente.obterNome() + " adicionado ao estoque!");
                }));
            }

            menuEstoque.addWidget(new Opcao("🔙 Voltar", (opcao) -> {
                mostrarDashboard();
            }));
        }

        ui.addWidget(infoEstoque);
        ui.addWidget(infoFaltantes);
        ui.addWidget(menuEstoque);
        ui.setWidgetAtivo(menuEstoque);
    }

    public static void mostrarAcoesRecomendadas() {
        ui.limparTudo();

        InfoBox infoAcoes = new InfoBox("💡 Ações Recomendadas");
        ArrayList<Acao> acoes = sistema.obterAcoesRecomendadas();

        Menu menuAcoes = new Menu("Opções");

        if (acoes.isEmpty()) {
            infoAcoes.adicionarLinha("✅ Nenhuma ação recomendada no momento!");
            infoAcoes.adicionarLinha("   O restaurante está funcionando bem.");
        } else {
            for (Acao acao : acoes) {
                infoAcoes.adicionarLinha("🔧 " + acao.obterNome());
                infoAcoes.adicionarLinha("   📝 " + acao.obterDescricao());
                infoAcoes.adicionarLinha("");
            }

            menuAcoes.addWidget(new Opcao("⚡ Executar Todas as Ações", (opcao) -> {
                executarTodasAcoes(acoes);
            }));
        }

        menuAcoes.addWidget(new Opcao("🔙 Voltar", (opcao) -> {
            mostrarDashboard();
        }));

        ui.addWidget(infoAcoes);
        ui.addWidget(menuAcoes);
        ui.setWidgetAtivo(menuAcoes);
    }

    public static void mostrarGerenciarPrecos() {
        ui.limparTudo();

        InfoBox infoPrecos = new InfoBox("💰 Preços Atuais");
        ArrayList<ItemCardapio> itens = restaurante.obteCardapio().obterItemsNoCardapio();

        for (ItemCardapio item : itens) {
            infoPrecos.adicionarLinha("🍽️ " + item.obterNome() + " - R$ " +
                    String.format("%.2f", item.obterPreco()) +
                    " (Aval: " + String.format("%.2f", item.obterAvaliacao()) + ")");
        }

        Menu menuPrecos = new Menu("Modificar Preços");

        for (ItemCardapio item : itens) {
            menuPrecos.addWidget(new Opcao("✏️ " + item.obterNome(), (opcao) -> {
                // Para simplicidade, vamos apenas ajustar preços em 10% para cima ou para baixo
                mostrarAjustarPreco(item);
            }));
        }

        menuPrecos.addWidget(new Opcao("🔙 Voltar", (opcao) -> {
            mostrarDashboard();
        }));

        ui.addWidget(infoPrecos);
        ui.addWidget(menuPrecos);
        ui.setWidgetAtivo(menuPrecos);
    }

    public static void mostrarAjustarPreco(ItemCardapio item) {
        ui.limparTudo();

        InfoBox infoItem = new InfoBox("💰 Ajustar Preço: " + item.obterNome());
        infoItem.adicionarLinha("Preço atual: R$ " + String.format("%.2f", item.obterPreco()));
        infoItem.adicionarLinha("Avaliação: " + String.format("%.2f", item.obterAvaliacao()));

        Menu menuAjuste = new Menu("Opções");

        float precoAtual = item.obterPreco();

        menuAjuste
                .addWidget(new Opcao("📈 Aumentar 10%", (opcao) -> {
                    item.definirPreco(precoAtual * 1.1f);
                    mostrarResultadoAcao("✅ Preço aumentado para R$ " + String.format("%.2f", item.obterPreco()));
                }))
                .addWidget(new Opcao("📉 Diminuir 10%", (opcao) -> {
                    item.definirPreco(precoAtual * 0.9f);
                    mostrarResultadoAcao("✅ Preço diminuído para R$ " + String.format("%.2f", item.obterPreco()));
                }))
                .addWidget(new Opcao("📈 Aumentar 25%", (opcao) -> {
                    item.definirPreco(precoAtual * 1.25f);
                    mostrarResultadoAcao("✅ Preço aumentado para R$ " + String.format("%.2f", item.obterPreco()));
                }))
                .addWidget(new Opcao("📉 Diminuir 25%", (opcao) -> {
                    item.definirPreco(precoAtual * 0.75f);
                    mostrarResultadoAcao("✅ Preço diminuído para R$ " + String.format("%.2f", item.obterPreco()));
                }))
                .addWidget(new Opcao("🔙 Voltar", (opcao) -> {
                    mostrarGerenciarPrecos();
                }));

        ui.addWidget(infoItem);
        ui.addWidget(menuAjuste);
        ui.setWidgetAtivo(menuAjuste);
    }

    public static void mostrarSalarios() {
        ui.limparTudo();

        InfoBox infoSalarios = new InfoBox("💵 Salários dos Funcionários");

        float totalSalarios = 0.0f;

        for (Funcionario func : restaurante.obterFuncionarios()) {
            float salario = calcularSalario(func.obterCargo());
            totalSalarios += salario;

            infoSalarios.adicionarLinha("👤 " + func.obterNome() +
                    " (" + func.obterCargo() + ") - R$ " + String.format("%.2f", salario));
        }

        infoSalarios.adicionarLinha("");
        infoSalarios.adicionarLinha("📊 Total semanal: R$ " + String.format("%.2f", totalSalarios));
        infoSalarios.adicionarLinha("📅 Total mensal: R$ " + String.format("%.2f", totalSalarios * 4));

        Menu menuSalarios = new Menu("Opções");
        menuSalarios.addWidget(new Opcao("🔙 Voltar", (opcao) -> {
            mostrarDashboard();
        }));

        ui.addWidget(infoSalarios);
        ui.addWidget(menuSalarios);
        ui.setWidgetAtivo(menuSalarios);
    }

    public static void mostrarResumoSimulacao() {
        ui.limparTudo();

        InfoBox resumo = new InfoBox("📊 Resumo da Simulação (30 dias)");

        resumo.adicionarLinha("💰 SITUAÇÃO FINANCEIRA:");
        resumo.adicionarLinha("   Cofre atual: R$ " + String.format("%.2f", restaurante.obterDinheiroNoCofre()));
        resumo.adicionarLinha(
                "   Rentabilidade média: R$ " + String.format("%.2f", calcularRentabilidadeMedia()) + "/dia");
        resumo.adicionarLinha("");

        resumo.adicionarLinha("👥 RECURSOS HUMANOS:");
        resumo.adicionarLinha("   Total funcionários: " + restaurante.obterFuncionarios().size());

        int altoDesempenho = 0, baixoDesempenho = 0;
        for (Funcionario func : restaurante.obterFuncionarios()) {
            if (func.desempenhoEstaAlto())
                altoDesempenho++;
            if (func.desempenhoEstaBaixo())
                baixoDesempenho++;
        }

        resumo.adicionarLinha("   🟢 Alto desempenho: " + altoDesempenho);
        resumo.adicionarLinha("   🔴 Baixo desempenho: " + baixoDesempenho);
        resumo.adicionarLinha("");

        resumo.adicionarLinha("🍽️ CARDÁPIO:");
        for (ItemCardapio item : restaurante.obteCardapio().obterItemsNoCardapio()) {
            boolean disponivel = !restaurante.obteCardapio()
                    .obterItemsEsgotados(restaurante.obterEstoque()).contains(item);
            String status = disponivel ? "✅" : "❌";

            resumo.adicionarLinha("   " + status + " " + item.obterNome() +
                    " - R$ " + String.format("%.2f", item.obterPreco()) +
                    " (Aval: " + String.format("%.2f", item.obterAvaliacao()) + ")");
        }

        Menu menuResumo = new Menu("Opções");
        menuResumo.addWidget(new Opcao("🔙 Voltar", (opcao) -> {
            mostrarDashboard();
        }));

        ui.addWidget(resumo);
        ui.addWidget(menuResumo);
        ui.setWidgetAtivo(menuResumo);
    }

    public static void mostrarResultadoAcao(String mensagem) {
        ui.limparTudo();

        InfoBox resultado = new InfoBox("📝 Resultado");
        resultado.adicionarLinha(mensagem);

        Menu menuContinuar = new Menu("Opções");
        menuContinuar.addWidget(new Opcao("✅ Continuar", (opcao) -> {
            mostrarDashboard();
        }));

        ui.addWidget(resultado);
        ui.addWidget(menuContinuar);
        ui.setWidgetAtivo(menuContinuar);
    }

    public static void executarTodasAcoes(ArrayList<Acao> acoes) {
        // Buscar gerente
        Funcionario gerente = null;
        for (Funcionario func : restaurante.obterFuncionarios()) {
            if (func.obterCargo() == Cargo.Gerente) {
                gerente = func;
                break;
            }
        }

        if (gerente == null) {
            mostrarResultadoAcao("❌ ERRO: Nenhum gerente disponível!");
            return;
        }

        int executadas = 0;
        for (Acao acao : acoes) {
            if (acao.verificarSePodeExecutar(gerente.obterCargo())) {
                acao.executar();
                executadas++;
            }
        }

        mostrarResultadoAcao("📊 " + executadas + " ações executadas com sucesso!");
    }

    public static void inicializarRestaurante() {
        restaurante = new Restaurante();

        // Adicionar funcionários iniciais
        restaurante.cadastrarFuncionario(new Funcionario("Alex", 28, Cargo.Gerente));
        restaurante.cadastrarFuncionario(new Funcionario("Lilian", 25, Cargo.Estoquista));
        restaurante.cadastrarFuncionario(new Funcionario("Fernanda", 29, Cargo.Faxineiro));
        restaurante.cadastrarFuncionario(new Funcionario("José", 28, Cargo.Cozinheiro));
        restaurante.cadastrarFuncionario(new Funcionario("Maria", 27, Cargo.Caixa));

        // Adicionar items no cardápio
        restaurante.obteCardapio().adicionarItemNoCardapio(new Hamburguer(), 32.00f);
        restaurante.obteCardapio().adicionarItemNoCardapio(new BatatasFritas(), 64.00f);
        restaurante.obteCardapio().adicionarItemNoCardapio(new Refrigerante(), 16.00f);

        sistema = new Sistema(restaurante);
    }

    public static void executarSimulacao30Dias() {
        System.out.println("🔄 Executando simulação de 30 dias...");
        System.out.println("Por favor, aguarde...\n");

        float dinheiroInicial = restaurante.obterDinheiroNoCofre();

        for (int i = 0; i < 30; i++) {
            float dinheiroAntes = restaurante.obterDinheiroNoCofre();

            // Simular receita do dia (estimativa simples)
            sistema.simularDia();

            float dinheiroDepois = restaurante.obterDinheiroNoCofre();
            float receitaDia = Math.max(0, dinheiroDepois - dinheiroAntes);

            // Estimar despesas (salários são pagos a cada 7 dias)
            float despesasDia = 0.0f;
            if (i % 7 == 6) { // Último dia da semana
                for (Funcionario func : restaurante.obterFuncionarios()) {
                    despesasDia += calcularSalario(func.obterCargo());
                }
            }

            historicoReceitas.add(receitaDia);
            historicoDespesas.add(despesasDia);

            System.out.println("Dia " + (i + 1) + " concluído. Cofre: R$ " +
                    String.format("%.2f", restaurante.obterDinheiroNoCofre()));
        }

        System.out.println("\n✅ Simulação de 30 dias concluída!");
        System.out.println("💰 Dinheiro inicial: R$ " + String.format("%.2f", dinheiroInicial));
        System.out.println("💰 Dinheiro final: R$ " + String.format("%.2f", restaurante.obterDinheiroNoCofre()));
        System.out
                .println("📈 Rentabilidade média: R$ " + String.format("%.2f", calcularRentabilidadeMedia()) + "/dia");
        System.out.println("\nPressione ENTER para acessar o Painel do Franqueado...");
        scanner.nextLine();
    }

    public static void main(String[] args) {
        // Inicializar o restaurante
        inicializarRestaurante();

        // Executar simulação de 30 dias primeiro
        executarSimulacao30Dias();

        // Após a simulação, mostrar o dashboard do franqueado
        mostrarDashboard();

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
                    System.out.println("\n=== 👋 ENCERRANDO SISTEMA ===");
                    System.out.println("Obrigado por usar o Painel do Franqueado!");
                    break;
                }

                ui.processarEvento(evento);
                ui.forceRedraw();
            }
        } finally {
            teclado.fechar();
            scanner.close();
        }
    }
}
