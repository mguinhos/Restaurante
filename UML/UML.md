# Código Fonte do UML PlanetUML

Cada UML (Diagrama de Classes) renderizados está na pasta renderizado pela ordem

## Classes Principais do Restaurante

```plantuml
@startuml

package restaurante {
  class Restaurante {
    - cardapio: Cardapio
    - estoque: Estoque
    - dinheiroNoCofre: float
    - funcionarios: ArrayList<Funcionario>
    + Restaurante()
    + cadastrarFuncionario(funcionario: Funcionario): void
    + demitirFuncionario(funcionario: Funcionario): void
    + promoverFuncionario(funcionario: Funcionario, novoCargo: Cargo): void
    + rebaixarFuncionario(funcionario: Funcionario, novoCargo: Cargo): void
    + obterDinheiroNoCofre(): float
    + adicionarDinheiroNoCofre(valor: float): void
    + removerDinheiroNoCofre(valor: float): boolean
    + processarPagamento(valorPago: float): void
    + obterFuncionarios(): ArrayList<Funcionario>
    + obteCardapio(): Cardapio
    + obterEstoque(): Estoque
    + obterFuncionarioPorNome(nome: String): Funcionario
  }

  class Cardapio {
    - items: ArrayList<ItemCardapio>
    + Cardapio()
    + adicionarDesconto(item: ItemCardapio, percentualDesconto: float): void
    + adicionarItemNoCardapio(alimento: Alimento, preco: float): void
    + adicionarItemNoCardapio(item: ItemCardapio): void
    + obterItemsNoCardapio(): ArrayList<ItemCardapio>
    + obterItemsEsgotados(estoque: Estoque): ArrayList<ItemCardapio>
  }

  class ItemCardapio {
    - alimento: Alimento
    - preco: float
    + ItemCardapio(alimento: Alimento, preco: float)
    + obterAlimento(): Alimento
    + obterPreco(): float
    + definirPreco(preco: float): void
    + obterNome(): String
    + obterCustoDeProducao(): float
    + obterLucro(): float
    + produzivel(estoque: Estoque): boolean
  }

  class Estoque {
    - ingredientes: ArrayList<Ingrediente>
    + Estoque()
    + possuiIngredientes(ingredientesNecessarios: ArrayList<Ingrediente>): boolean
    + obterIngredientes(): ArrayList<Ingrediente>
    + removerIngredientes(ingredientesRemover: ArrayList<Ingrediente>): void
    + adicionarIngrediente(ingrediente: Ingrediente): void
  }

  class Pedido {
    - cliente: Cliente
    - item: ItemCardapio
    - caixa: Funcionario
    - cozinheiro: Funcionario
    - foiRecomendado: boolean
    + Pedido(cliente: Cliente, item: ItemCardapio, caixa: Funcionario)
    + obterCliente(): Cliente
    + obterItem(): ItemCardapio
    + obterCaixa(): Funcionario
    + obterCozinheiro(): Funcionario
    + foiRecomendado(): boolean
    + definirCozinheiro(cozinheiro: Funcionario): void
    + marcarComoRecomendado(): void
  }

  class Sistema {
    - restaurante: Restaurante
    - diasDecorridos: int
    - demandasItens: Map<String, Integer>
    - vendasItens: Map<String, Integer>
    - avaliacoesTotaisItens: Map<String, Float>
    - numeroAvaliacoesItens: Map<String, Integer>
    + Sistema(restaurante: Restaurante)
    + obterAcoesRecomendadas(): ArrayList<Acao>
    + obterFuncionariosComAltoDesempenho(): ArrayList<Funcionario>
    + obterFuncionariosComBaixoDesempenho(): ArrayList<Funcionario>
    + obterIngredientesQueFaltam(): ArrayList<Ingrediente>
    + obterIngredientesNecessarios(): ArrayList<Ingrediente>
    + obterAlimentosComPiorDesempenho(): ArrayList<ItemCardapio>
    + simularDia(): void
  }

  abstract class Avaliavel {
    # avaliacoes: ArrayList<Float>
    + Avaliavel()
    + obterAvaliacao(): float
    + avaliar(avaliacao: float): void
    + desempenhoEstaBaixo(): boolean
    + desempenhoEstaAlto(): boolean
  }

  enum Cargo {
    Gerente
    Cozinheiro
    Caixa
    Estoquista
    Faxineiro
  }

  enum ClasseSocial {
    BaixaRenda
    MediaRenda
    AltaRenda
  }

  ItemCardapio --|> Avaliavel
  Restaurante o-- Cardapio
  Restaurante o-- Estoque
  Restaurante o-- "0..*" Funcionario
  Cardapio o-- "0..*" ItemCardapio
  ItemCardapio o-- Alimento
  Sistema o-- Restaurante
}

@enduml
```

## Classes de Pessoa e Funcionários

```plantuml
@startuml

package restaurante.pessoa {
  interface Pessoa {
    + obterNome(): String
    + obterIdade(): int
  }

  class Cliente {
    - nome: String
    - idade: int
    - dinheiro: float
    - classeSocial: ClasseSocial
    - ingredientesQueGosta: ArrayList<Ingrediente>
    - ingredientesQueDesgosta: ArrayList<Ingrediente>
    - alimentosQueGosta: ArrayList<Alimento>
    - alimentosQueDesgosta: ArrayList<Alimento>
    + Cliente(nome: String, idade: int)
    + Cliente(nome: String, idade: int, classeSocial: ClasseSocial, dinheiro: float)
    + obterNome(): String
    + obterIdade(): int
    + obterClasseSocial(): ClasseSocial
    + obterDinheiro(): float
    + possuiDinheiro(valor: float): boolean
    + vaiDesistirPorPreco(preco: float): boolean
    + pagar(valor: float): boolean
    + adicionarDinheiro(valor: float): void
    + adicionarIngredienteQueGosta(ingrediente: Ingrediente): void
    + adicionarIngredienteQueDesgosta(ingrediente: Ingrediente): void
    + obterIngredientesQueGosta(): ArrayList<Ingrediente>
    + obterIngredientesQueDesgosta(): ArrayList<Ingrediente>
    + adicionarAlimentoQueGosta(alimento: Alimento): void
    + adicionarAlimentoQueDesgosta(alimento: Alimento): void
    + obterAlimentosQueGosta(): ArrayList<Alimento>
    + obterAlimentosQueDesgosta(): ArrayList<Alimento>
    + calcularRatingParaAlimento(alimento: Alimento): float
    + gostaDoIngrediente(ingrediente: Ingrediente): boolean
    + desgostaDoIngrediente(ingrediente: Ingrediente): boolean
    + gostaDoAlimento(alimento: Alimento): boolean
    + desgostaDoAlimento(alimento: Alimento): boolean
  }

  class Funcionario {
    - nome: String
    - idade: int
    - cargo: Cargo
    + Funcionario(nome: String, idade: int, cargo: Cargo)
    + obterNome(): String
    + obterIdade(): int
    + obterCargo(): Cargo
    + definirCargo(novoCargo: Cargo): void
    + restaurarAvaliacaoParcial(): void
    + recomendarAlimento(cardapio: Cardapio): ItemCardapio
  }

  Cliente ..|> Pessoa
  Funcionario ..|> Pessoa
  Funcionario --|> Avaliavel
}

@enduml
```

## Classes de Alimentos

```plantuml
@startuml

package restaurante.alimento {
  abstract class Alimento {
    + {abstract} obterNome(): String
    + {abstract} obterIngredientes(): ArrayList<Ingrediente>
    + obterCustoDeProducao(): float
    + produzivel(estoque: Estoque): boolean
  }

  class Hamburguer {
    + obterNome(): String
    + obterIngredientes(): ArrayList<Ingrediente>
  }

  class BatatasFritas {
    + obterNome(): String
    + obterIngredientes(): ArrayList<Ingrediente>
  }

  class Refrigerante {
    + obterNome(): String
    + obterIngredientes(): ArrayList<Ingrediente>
  }

  class CheeseBurguer {
    + obterNome(): String
    + obterIngredientes(): ArrayList<Ingrediente>
  }
  
  class BaconBurguer {
    + obterNome(): String
    + obterIngredientes(): ArrayList<Ingrediente>
  }
  
  class BigBurguer {
    + obterNome(): String
    + obterIngredientes(): ArrayList<Ingrediente>
  }
  
  class ChickenBurguer {
    + obterNome(): String
    + obterIngredientes(): ArrayList<Ingrediente>
  }
  
  class MilkShake {
    + obterNome(): String
    + obterIngredientes(): ArrayList<Ingrediente>
  }
  
  class Suco {
    + obterNome(): String
    + obterIngredientes(): ArrayList<Ingrediente>
  }
  
  class VeggieBurguer {
    + obterNome(): String
    + obterIngredientes(): ArrayList<Ingrediente>
  }

  Hamburguer --|> Alimento
  BatatasFritas --|> Alimento
  Refrigerante --|> Alimento
  CheeseBurguer --|> Alimento
  BaconBurguer --|> Alimento
  BigBurguer --|> Alimento
  ChickenBurguer --|> Alimento
  MilkShake --|> Alimento
  Suco --|> Alimento
  VeggieBurguer --|> Alimento
}

@enduml
```

## Classes de Ingredientes

```plantuml
@startuml

package restaurante.ingrediente {
  abstract class Ingrediente {
    + {abstract} obterNome(): String
    + {abstract} obterPreco(): float
  }

  class PaoDeHamburguer {
    + obterNome(): String
    + obterPreco(): float
  }

  class FatiaTomate {
    + obterNome(): String
    + obterPreco(): float
  }

  class CarneDeHamburguer {
    + obterNome(): String
    + obterPreco(): float
  }
  
  class Batata {
    + obterNome(): String
    + obterPreco(): float
  }
  
  class MeioLitroDeAguaGaseificada {
    + obterNome(): String
    + obterPreco(): float
  }
  
  class RefrigeranteEmPo {
    + obterNome(): String
    + obterPreco(): float
  }

  class Alface {
    + obterNome(): String
    + obterPreco(): float
  }
  
  class Bacon {
    + obterNome(): String
    + obterPreco(): float
  }
  
  class CaldasChocolate {
    + obterNome(): String
    + obterPreco(): float
  }
  
  class CarneDeSoja {
    + obterNome(): String
    + obterPreco(): float
  }
  
  class CarneFrango {
    + obterNome(): String
    + obterPreco(): float
  }
  
  class FatiaCebola {
    + obterNome(): String
    + obterPreco(): float
  }
  
  class FrutaCitrica {
    + obterNome(): String
    + obterPreco(): float
  }
  
  class Maionese {
    + obterNome(): String
    + obterPreco(): float
  }
  
  class MeioLitroAguaMineral {
    + obterNome(): String
    + obterPreco(): float
  }
  
  class MeioLitroLeite {
    + obterNome(): String
    + obterPreco(): float
  }
  
  class MolhoBarbecue {
    + obterNome(): String
    + obterPreco(): float
  }
  
  class MolhoKetchup {
    + obterNome(): String
    + obterPreco(): float
  }
  
  class MolhoMostarda {
    + obterNome(): String
    + obterPreco(): float
  }
  
  class Picles {
    + obterNome(): String
    + obterPreco(): float
  }
  
  class QueijoCheddar {
    + obterNome(): String
    + obterPreco(): float
  }
  
  class QueijoMussarela {
    + obterNome(): String
    + obterPreco(): float
  }
  
  class SorveteBaunilha {
    + obterNome(): String
    + obterPreco(): float
  }

  PaoDeHamburguer --|> Ingrediente
  FatiaTomate --|> Ingrediente
  CarneDeHamburguer --|> Ingrediente
  Batata --|> Ingrediente
  MeioLitroDeAguaGaseificada --|> Ingrediente
  RefrigeranteEmPo --|> Ingrediente
  Alface --|> Ingrediente
  Bacon --|> Ingrediente
  CaldasChocolate --|> Ingrediente
  CarneDeSoja --|> Ingrediente
  CarneFrango --|> Ingrediente
  FatiaCebola --|> Ingrediente
  FrutaCitrica --|> Ingrediente
  Maionese --|> Ingrediente
  MeioLitroAguaMineral --|> Ingrediente
  MeioLitroLeite --|> Ingrediente
  MolhoBarbecue --|> Ingrediente
  MolhoKetchup --|> Ingrediente
  MolhoMostarda --|> Ingrediente
  Picles --|> Ingrediente
  QueijoCheddar --|> Ingrediente
  QueijoMussarela --|> Ingrediente
  SorveteBaunilha --|> Ingrediente
}

@enduml
```

## Classes de Ações

```plantuml
@startuml

package restaurante.acao {
  abstract class Acao {
    + {abstract} executar(): void
    + {abstract} obterNome(): String
    + {abstract} obterDescricao(): String
    + verificarSePodeExecutar(cargo: Cargo): boolean
  }

  class AplicarDesconto {
    - cardapio: Cardapio
    - item: ItemCardapio
    - percentualDesconto: float
    + AplicarDesconto(cardapio: Cardapio, item: ItemCardapio, percentualDesconto: float)
    + executar(): void
    + obterNome(): String
    + obterDescricao(): String
    + verificarSePodeExecutar(cargo: Cargo): boolean
  }

  class AumentarPreco {
    - cardapio: Cardapio
    - item: ItemCardapio
    - percentualAumento: float
    + AumentarPreco(cardapio: Cardapio, item: ItemCardapio, percentualAumento: float)
    + executar(): void
    + obterNome(): String
    + obterDescricao(): String
    + verificarSePodeExecutar(cargo: Cargo): boolean
  }

  class BaixarPreco {
    - cardapio: Cardapio
    - item: ItemCardapio
    - percentualReducao: float
    + BaixarPreco(cardapio: Cardapio, item: ItemCardapio, percentualReducao: float)
    + executar(): void
    + obterNome(): String
    + obterDescricao(): String
    + verificarSePodeExecutar(cargo: Cargo): boolean
  }

  class DeclararFalencia {
    - restaurante: Restaurante
    + DeclararFalencia(restaurante: Restaurante)
    + executar(): void
    + obterNome(): String
    + obterDescricao(): String
    + verificarSePodeExecutar(cargo: Cargo): boolean
  }

  class DemitirFuncionario {
    - restaurante: Restaurante
    - funcionario: Funcionario
    + DemitirFuncionario(restaurante: Restaurante, funcionario: Funcionario)
    + executar(): void
    + obterNome(): String
    + obterDescricao(): String
    + verificarSePodeExecutar(cargo: Cargo): boolean
  }

  class PromoverFuncionario {
    - restaurante: Restaurante
    - funcionario: Funcionario
    - novoCargo: Cargo
    + PromoverFuncionario(restaurante: Restaurante, funcionario: Funcionario, novoCargo: Cargo)
    + executar(): void
    + obterNome(): String
    + obterDescricao(): String
    + verificarSePodeExecutar(cargo: Cargo): boolean
  }

  class RebaixarFuncionario {
    - restaurante: Restaurante
    - funcionario: Funcionario
    - novoCargo: Cargo
    + RebaixarFuncionario(restaurante: Restaurante, funcionario: Funcionario, novoCargo: Cargo)
    + executar(): void
    + obterNome(): String
    + obterDescricao(): String
    + verificarSePodeExecutar(cargo: Cargo): boolean
  }

  class ReporIngredienteNoEstoque {
    - estoque: Estoque
    - restaurante: Restaurante
    - ingrediente: Ingrediente
    + ReporIngredienteNoEstoque(estoque: Estoque, ingrediente: Ingrediente)
    + ReporIngredienteNoEstoque(restaurante: Restaurante, ingrediente: Ingrediente)
    + executar(): void
    + obterNome(): String
    + obterDescricao(): String
    + verificarSePodeExecutar(cargo: Cargo): boolean
  }

  AplicarDesconto --|> Acao
  AumentarPreco --|> Acao
  BaixarPreco --|> Acao
  DeclararFalencia --|> Acao
  DemitirFuncionario --|> Acao
  PromoverFuncionario --|> Acao
  RebaixarFuncionario --|> Acao
  ReporIngredienteNoEstoque --|> Acao
}

@enduml
```

## Componentes da UI (Main)

```plantuml
@startuml

package restaurante {
  class Main {
    + {static} main(args: String[]): void
    - {static} limparTela(): void
    - {static} calcularRentabilidadeMedia(): float
    - {static} calcularSalario(cargo: Cargo): float
    - {static} obterCargoSuperior(cargoAtual: Cargo): Cargo
    - {static} obterCargoInferior(cargoAtual: Cargo): Cargo
    - {static} mostrarDashboard(): void
    - {static} mostrarGerenciarFuncionarios(): void
    - {static} mostrarGerenciarEstoque(): void
    - {static} mostrarAcoesRecomendadas(): void
    - {static} executarTodasAcoes(acoes: ArrayList<Acao>): void
  }

  enum TipoEvento {
    KEY_UP
    KEY_DOWN
    ENTER
    ESC
  }

  class EventoTeclado {
    - tipo: TipoEvento
    + EventoTeclado(tipo: TipoEvento)
  }

  interface Focusable {
    + onFocus(): void
    + onBlur(): void
    + isFocused(): boolean
  }

  interface Drawable {
    + getWidth(): int
    + getHeight(): int
    + renderLines(): String[]
  }

  abstract class Widget {
    # children: Vector<Widget>
    # parent: Widget
    # width: int
    # height: int
    # paddingTop: int
    # paddingBottom: int
    # paddingLeft: int
    # paddingRight: int
    + Widget()
    + addWidget(widget: Widget): Widget
    + {abstract} exibir(): String
    + processarEvento(evento: EventoTeclado): Widget
    + encontrarProximoFocavel(): Widget
    + encontrarAnteriorFocavel(): Widget
    # calculateDimensions(): void
    # drawBox(content: String[]): String[]
    + getWidth(): int
    + getHeight(): int
    + renderLines(): String[]
  }

  class Label {
    - texto: String
    + Label(texto: String)
    + exibir(): String
    + renderLines(): String[]
    + setText(texto: String): void
  }

  class Opcao {
    - texto: String
    - funcao: Consumer<Opcao>
    - focused: boolean
    - uiRef: UI
    + Opcao(texto: String, funcao: Consumer<Opcao>)
    + setUIReference(ui: UI): void
    + processarEvento(evento: EventoTeclado): Widget
    + exibir(): String
    + onFocus(): void
    + onBlur(): void
    + isFocused(): boolean
  }

  class Menu {
    - selectedIndex: int
    - titulo: String
    + Menu()
    + Menu(titulo: String)
    + addWidget(widget: Widget): Widget
    # calculateDimensions(): void
    + exibir(): String
    + renderLines(): String[]
    + processarEvento(evento: EventoTeclado): Widget
    + getSelectedIndex(): int
  }

  class InfoBox {
    - titulo: String
    - linhas: Vector<String>
    + InfoBox(titulo: String)
    + adicionarLinha(linha: String): void
    + limpar(): void
    + exibir(): String
    + renderLines(): String[]
  }

  class UI {
    - widgetAtivo: Widget
    - needsRedraw: boolean
    + UI()
    + addWidget(widget: Widget): Widget
    - setUIReferenceRecursive(widget: Widget): void
    + forceRedraw(): void
    + exibir(): String
    + renderLines(): String[]
    + processarEvento(evento: EventoTeclado): Widget
    + setWidgetAtivo(widget: Widget): void
    + needsRedraw(): boolean
    + setRedrawComplete(): void
    + limparTudo(): void
  }

  class TecladoHandler {
    - scanner: Scanner
    + TecladoHandler()
    + lerProximoEvento(): EventoTeclado
    + fechar(): void
  }

  Label --|> Widget
  Opcao --|> Widget
  Opcao ..|> Focusable
  Menu --|> Widget
  InfoBox --|> Widget
  UI --|> Widget
  Widget ..|> Drawable
}

@enduml
```

## Relações entre Pacotes Principais

```plantuml
@startuml

package restaurante {
}

package restaurante.pessoa {
}

package restaurante.alimento {
}

package restaurante.ingrediente {
}

package restaurante.acao {
}

package restaurante.erros {
  class FuncionarioNaoEncontradoErro {
    + FuncionarioNaoEncontradoErro(message: String)
    + FuncionarioNaoEncontradoErro(message: String, cause: Throwable)
  }
}

restaurante --> restaurante.pessoa : usa
restaurante --> restaurante.alimento : usa
restaurante --> restaurante.ingrediente : usa
restaurante --> restaurante.acao : usa
restaurante --> restaurante.erros : usa
restaurante.alimento --> restaurante.ingrediente : usa
restaurante.acao --> restaurante : manipula

@enduml
```