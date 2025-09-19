package restaurante;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import restaurante.acao.Acao;
import restaurante.acao.AumentarPreco;
import restaurante.acao.BaixarPreco;
import restaurante.acao.DeclararFalencia;
import restaurante.acao.DemitirFuncionario;
import restaurante.acao.PromoverFuncionario;
import restaurante.acao.RebaixarFuncionario;
import restaurante.acao.ReporIngredienteNoEstoque;
import restaurante.ingrediente.Ingrediente;
import restaurante.pessoa.Cliente;
import restaurante.pessoa.Funcionario;

public class Sistema {
    Restaurante restaurante;
    private int diasDecorridos = 0;

    private Map<String, Integer> demandasItens = new HashMap<>();
    private Map<String, Integer> vendasItens = new HashMap<>();

    private Map<String, Float> avaliacoesTotaisItens = new HashMap<>();
    private Map<String, Integer> numeroAvaliacoesItens = new HashMap<>();


/**
 * Construtor da classe Sistema. Inicializa o sistema de simulação e análise
 * do restaurante, criando mapas para controlar demanda, vendas e avaliações
 * de todos os itens do cardápio.
 * 
 * paramentro:  restaurante Instância do restaurante a ser gerenciado pelo sistema
 */
    Sistema(Restaurante restaurante) {
        this.restaurante = restaurante;

        for (ItemCardapio item : restaurante.obteCardapio().obterItemsNoCardapio()) {
            demandasItens.put(item.obterNome(), 0);
            vendasItens.put(item.obterNome(), 0);
            avaliacoesTotaisItens.put(item.obterNome(), 0.0f);
            numeroAvaliacoesItens.put(item.obterNome(), 0);
        }
    }

/**
 * Analisa o estado atual do restaurante e retorna uma lista de ações recomendadas
 * baseadas em dados de desempenho, situação financeira e análise de funcionários.
 * Implementa um sistema de IA simulado para sugestões estratégicas.
 * 
 * return: ArrayList<Acao> Lista de ações recomendadas para otimizar o restaurante
 */
    public ArrayList<Acao> obterAcoesRecomendadas() {
        ArrayList<Acao> acoes_recomendadas = new ArrayList<Acao>();

        if (restaurante.obterDinheiroNoCofre() < 50.0f) {
            System.out.println("ALERTA CRÍTICO: Dinheiro insuficiente no cofre!");
            acoes_recomendadas.add(new DeclararFalencia(restaurante));
            return acoes_recomendadas;
        }

        analisarDemandaItens(acoes_recomendadas);

        for (Funcionario funcionario : this.obterFuncionariosComBaixoDesempenho()) {
            Cargo cargoInferior = obterCargoInferior(funcionario.obterCargo());
            if (cargoInferior != null && funcionario.obterAvaliacao() > 0.15f) {
                acoes_recomendadas.add(new RebaixarFuncionario(this.restaurante, funcionario, cargoInferior));
            } else {
                acoes_recomendadas.add(new DemitirFuncionario(this.restaurante, funcionario));
            }
        }

        for (Funcionario funcionario : this.obterFuncionariosComAltoDesempenho()) {
            Cargo cargoSuperior = obterCargoSuperior(funcionario.obterCargo());
            if (cargoSuperior != null) {
                acoes_recomendadas.add(new PromoverFuncionario(this.restaurante, funcionario, cargoSuperior));
            }
        }

        for (Ingrediente ingrediente : this.obterIngredientesQueFaltam()) {
            acoes_recomendadas.add(new ReporIngredienteNoEstoque(this.restaurante, ingrediente));
        }

        return acoes_recomendadas;
    }

/**
 * Analisa a demanda e performance de cada item do cardápio, identificando
 * padrões de alta/baixa demanda e bom/mau desempenho para recomendar
 * ajustes de preços estratégicos.
 * 
 * paramentro: acoes_recomendadas Lista onde as ações de ajuste de preços serão adicionadas
 */
    private void analisarDemandaItens(ArrayList<Acao> acoes_recomendadas) {
        for (ItemCardapio item : restaurante.obteCardapio().obterItemsNoCardapio()) {
            String nomeItem = item.obterNome();
            int demanda = demandasItens.getOrDefault(nomeItem, 0);
            int vendas = vendasItens.getOrDefault(nomeItem, 0);

            float avaliacaoMedia = calcularAvaliacaoMedia(nomeItem);

            boolean altaDemanda = demanda >= 8 && vendas >= 3;
            boolean baixaDemanda = demanda <= 2 && vendas <= 1;
            boolean bomDesempenho = avaliacaoMedia >= 0.7f && numeroAvaliacoesItens.get(nomeItem) >= 3;
            boolean mauDesempenho = avaliacaoMedia <= 0.4f && numeroAvaliacoesItens.get(nomeItem) >= 3;

            if (altaDemanda && bomDesempenho) {
                System.out.println("ITEM PREMIUM detectado: " + nomeItem +
                        " (Demanda: " + demanda + ", Vendas: " + vendas +
                        ", Avaliação: " + String.format("%.2f", avaliacaoMedia) + ")");
                acoes_recomendadas.add(new AumentarPreco(restaurante.obteCardapio(), item, 25.0f));
            }

            else if (altaDemanda) {
                System.out.println("ALTA DEMANDA detectada para " + nomeItem +
                        " (Demanda: " + demanda + ", Vendas: " + vendas +
                        ", Avaliação: " + String.format("%.2f", avaliacaoMedia) + ")");
                acoes_recomendadas.add(new AumentarPreco(restaurante.obteCardapio(), item, 15.0f));
            }

            else if (mauDesempenho) {
                System.out.println("MAU DESEMPENHO detectado para " + nomeItem +
                        " (Demanda: " + demanda + ", Vendas: " + vendas +
                        ", Avaliação: " + String.format("%.2f", avaliacaoMedia) + ")");
                acoes_recomendadas.add(new BaixarPreco(restaurante.obteCardapio(), item, 20.0f));
            }

            else if (baixaDemanda) {
                System.out.println("BAIXA DEMANDA detectada para " + nomeItem +
                        " (Demanda: " + demanda + ", Vendas: " + vendas +
                        ", Avaliação: " + String.format("%.2f", avaliacaoMedia) + ")");
                acoes_recomendadas.add(new BaixarPreco(restaurante.obteCardapio(), item, 10.0f));
            }
        }
    }

/**
 * Calcula a avaliação média de um item específico do cardápio baseado
 * no histórico de avaliações dos clientes.
 * 
 * paramentro: nomeItem Nome do item para calcular a avaliação média
 * return: float Avaliação média entre 0.0 e 1.0, retorna 0.5 se não houver avaliações
 */
    private float calcularAvaliacaoMedia(String nomeItem) {
        int numeroAvaliacoes = numeroAvaliacoesItens.getOrDefault(nomeItem, 0);
        if (numeroAvaliacoes == 0) {
            return 0.5f;
        }
        float totalAvaliacoes = avaliacoesTotaisItens.getOrDefault(nomeItem, 0.0f);
        return totalAvaliacoes / numeroAvaliacoes;
    }

/**
 * Identifica funcionários com desempenho superior à média para possíveis
 * promoções ou reconhecimento. Parte do sistema de análise de recursos humanos.
 * 
 * return: ArrayList<Funcionario> Lista de funcionários com alto desempenho
 */
    public ArrayList<Funcionario> obterFuncionariosComAltoDesempenho() {
        ArrayList<Funcionario> funcionarios_com_alto_desempenho = new ArrayList<Funcionario>();
        for (Funcionario funcionario : this.restaurante.obterFuncionarios()) {
            if (funcionario.desempenhoEstaAlto()) {
                funcionarios_com_alto_desempenho.add(funcionario);
            }
        }
        return funcionarios_com_alto_desempenho;
    }

/**
 * Identifica funcionários com desempenho abaixo da média que podem necessitar
 * de treinamento, rebaixamento ou demissão. Parte da análise de RH.
 * 
 * return: ArrayList<Funcionario> Lista de funcionários com baixo desempenho
 */
    public ArrayList<Funcionario> obterFuncionariosComBaixoDesempenho() {
        ArrayList<Funcionario> funcionarios_com_baixo_desempenho = new ArrayList<Funcionario>();

        for (Funcionario funcionario : this.restaurante.obterFuncionarios()) {
            if (funcionario.desempenhoEstaBaixo()) {
                funcionarios_com_baixo_desempenho.add(funcionario);
            }
        }

        return funcionarios_com_baixo_desempenho;
    }

/**
 * Analisa o estoque e identifica quais ingredientes estão em falta para
 * produzir todos os itens do cardápio, auxiliando no controle de estoque.
 * 
 * return: ArrayList<Ingrediente> Lista de ingredientes que precisam ser repostos
 */
    public ArrayList<Ingrediente> obterIngredientesQueFaltam() {
        ArrayList<Ingrediente> ingredientes_que_faltam = new ArrayList<Ingrediente>();

        ArrayList<Ingrediente> ingredientes_necessarios = this.obterIngredientesNecessarios();
        ArrayList<Ingrediente> ingredientes_em_estoque = this.restaurante.obterEstoque().obterIngredientes();

        for (Ingrediente necessario : ingredientes_necessarios) {
            boolean esta_em_estoque = false;
            for (Ingrediente em_estoque : ingredientes_em_estoque) {
                if (em_estoque.obterNome().equals(necessario.obterNome())) {
                    esta_em_estoque = true;
                    break;
                }
            }

            boolean ja_na_lista = false;
            for (Ingrediente faltante : ingredientes_que_faltam) {
                if (faltante.obterNome().equals(necessario.obterNome())) {
                    ja_na_lista = true;
                    break;
                }
            }

            if (!esta_em_estoque && !ja_na_lista) {
                ingredientes_que_faltam.add(necessario);
            }
        }

        return ingredientes_que_faltam;
    }

/**
 * Determina todos os ingredientes necessários para produzir os itens
 * que estão esgotados no cardápio, baseado na composição dos alimentos.
 * 
 * return: ArrayList<Ingrediente> Lista de ingredientes necessários para itens esgotados
 */
    public ArrayList<Ingrediente> obterIngredientesNecessarios() {
        ArrayList<Ingrediente> ingredientes_necessarios = new ArrayList<Ingrediente>();
        for (ItemCardapio item : this.restaurante.obteCardapio().obterItemsEsgotados(this.restaurante.obterEstoque())) {
            for (Ingrediente ingrediente : item.obterAlimento().obterIngredientes()) {
                ingredientes_necessarios.add(ingrediente);
            }
        }

        return ingredientes_necessarios;
    }

/**
 * Identifica itens do cardápio com desempenho ruim baseado em avaliações
 * e vendas, auxiliando na tomada de decisões sobre o cardápio.
 * 
 * return: ArrayList<ItemCardapio> Lista de itens com pior desempenho
 */
    public ArrayList<ItemCardapio> obterAlimentosComPiorDesempenho() {
        ArrayList<ItemCardapio> itens_com_pior_desempenho = new ArrayList<ItemCardapio>();

        for (ItemCardapio item : this.restaurante.obteCardapio().obterItemsNoCardapio()) {
            if (item.desempenhoEstaBaixo()) {
                itens_com_pior_desempenho.add(item);
            }
        }

        return itens_com_pior_desempenho;
    }

/**
 * Executa a simulação completa de um dia de operação do restaurante,
 * incluindo geração de clientes, processamento de pedidos, avaliações
 * de funcionários e análise financeira. Método principal da simulação.
 */
    public void simularDia() {
        diasDecorridos++;
        System.out.println("=== INICIANDO SIMULAÇÃO DO DIA " + diasDecorridos + " ===");

        if (diasDecorridos % 7 == 0) {
            pagarSalarios();
        }

        for (String item : demandasItens.keySet()) {
            demandasItens.put(item, 0);
            vendasItens.put(item, 0);

        }

        System.out.println("\n=== VERIFICAÇÃO DE GERÊNCIA ===");
        Funcionario gerente = obterGerente();
        if (gerente == null) {
            System.out.println("ALERTA CRÍTICO: Nenhum gerente encontrado no restaurante!");
            System.out.println("Iniciando processo de promoção urgente...");

            Funcionario candidatoGerente = encontrarMelhorCandidatoParaGerente();

            if (candidatoGerente != null) {
                Cargo cargoAnterior = candidatoGerente.obterCargo();
                candidatoGerente.definirCargo(Cargo.Gerente);
                candidatoGerente.avaliar(0.9f);
                gerente = candidatoGerente;

                System.out.println("PROMOÇÃO URGENTE: " + candidatoGerente.obterNome() +
                        " foi promovido de " + cargoAnterior + " para Gerente!");
                System.out.println("Motivo: Necessidade operacional crítica");
            } else {
                System.out.println("ERRO CRÍTICO: Não há funcionários qualificados para promoção a gerente!");
                System.out.println("Restaurante operando sem gerência - operações limitadas");
            }
        } else {
            System.out.println("Gerente atual: " + gerente.obterNome() +
                    " (Avaliação: " + String.format("%.2f", gerente.obterAvaliacao()) + ")");
        }

        System.out.println("\n=== SITUAÇÃO FINANCEIRA INICIAL ===");
        System.out.println("Dinheiro no cofre: R$ " + String.format("%.2f", restaurante.obterDinheiroNoCofre()));

        System.out.println("\n=== AÇÕES GERENCIAIS ===");
        if (gerente != null) {
            ArrayList<Acao> acoesRecomendadas = this.obterAcoesRecomendadas();
            Random random = new Random();

            for (Acao acao : acoesRecomendadas) {
                if (acao.verificarSePodeExecutar(gerente.obterCargo())) {
                    if (random.nextFloat() < 0.9f) {
                        System.out.println("Gerente " + gerente.obterNome() + " executou: " + acao.obterNome());
                        System.out.println("  Descrição: " + acao.obterDescricao());
                        acao.executar();
                        gerente.avaliar(0.7f);
                    } else {
                        System.out.println(
                                "Gerente " + gerente.obterNome() + " decidiu não executar: " + acao.obterNome());
                    }
                } else {
                    System.out
                            .println("Gerente " + gerente.obterNome() + " não tem permissão para: " + acao.obterNome());
                }
            }

            if (acoesRecomendadas.isEmpty()) {
                System.out.println("Nenhuma ação recomendada para hoje. Gerente " + gerente.obterNome()
                        + " está satisfeito com o estado atual.");
            }
        } else {
            System.out.println("OPERAÇÃO LIMITADA: Sem gerente, apenas operações básicas serão realizadas.");
        }

        ArrayList<Cliente> clientes = gerarClientesRandomicos();
        ArrayList<Pedido> pedidos = new ArrayList<Pedido>();

        ArrayList<ItemCardapio> itensEsgotados = restaurante.obteCardapio()
                .obterItemsEsgotados(restaurante.obterEstoque());

        System.out.println("\n=== PREPARAÇÃO DO DIA ===");
        System.out.println("Clientes gerados: " + clientes.size());
        System.out.println("Itens esgotados: " + itensEsgotados.size());
        for (ItemCardapio item : itensEsgotados) {
            System.out.println("  - " + item.obterNome() + " está esgotado");
        }

        System.out.println("\n=== PROCESSANDO PEDIDOS ===");
        float receitaDia = 0.0f;
        int clientesSemDinheiro = 0;
        int clientesItemEsgotado = 0;
        int clientesDesistiramPreco = 0;

        for (Cliente cliente : clientes) {
            System.out.println("\nCliente: " + cliente.obterNome() + " chegou");
            System.out.println("  Dinheiro do cliente: R$ " + String.format("%.2f", cliente.obterDinheiro()));
            System.out.println("  Classe social: " + cliente.obterClasseSocial());

            Funcionario caixa = obterCaixaDisponivel();
            if (caixa == null) {
                System.out.println("  ERRO: Nenhum caixa disponível");
                continue;
            }
            System.out.println("  Atendido pelo caixa: " + caixa.obterNome());

            ItemCardapio itemEscolhido;
            boolean foiRecomendado = new Random().nextBoolean();

            if (foiRecomendado) {
                itemEscolhido = caixa.recomendarAlimento(restaurante.obteCardapio());
                System.out.println("  Cliente pediu recomendação");
                System.out.println("  Caixa recomendou: "
                        + (itemEscolhido != null ? itemEscolhido.obterNome() : "nada disponível"));
            } else {
                itemEscolhido = escolherItemAleatorio();
                System.out.println("  Cliente escolheu: "
                        + (itemEscolhido != null ? itemEscolhido.obterNome() : "nada disponível"));
            }

            if (itemEscolhido == null) {
                System.out.println("  ERRO: Nenhum item disponível no cardápio");
                continue;
            }

            String nomeItem = itemEscolhido.obterNome();
            demandasItens.put(nomeItem, demandasItens.getOrDefault(nomeItem, 0) + 1);

            float precoItem = itemEscolhido.obterPreco();
            System.out.println("  Preço do item: R$ " + String.format("%.2f", precoItem));

            if (cliente.vaiDesistirPorPreco(precoItem)) {
                System.out.println("  DESISTÊNCIA: Cliente da classe " + cliente.obterClasseSocial() +
                        " achou o preço muito alto!");
                System.out.println("  Percentual do dinheiro: " +
                        String.format("%.1f", (precoItem / cliente.obterDinheiro()) * 100) + "%");
                clientesDesistiramPreco++;
                continue;
            }

            if (!cliente.possuiDinheiro(precoItem)) {
                System.out.println("  PROBLEMA: Cliente não tem dinheiro suficiente!");
                System.out.println("  Dinheiro necessário: R$ " + String.format("%.2f", precoItem));
                System.out.println("  Dinheiro do cliente: R$ " + String.format("%.2f", cliente.obterDinheiro()));
                clientesSemDinheiro++;
                continue;
            }

            if (itensEsgotados.contains(itemEscolhido)) {
                System.out.println("  PROBLEMA: Item " + itemEscolhido.obterNome() + " está esgotado!");
                clientesItemEsgotado++;

                Funcionario estoquista = obterEstoquista();
                if (estoquista != null) {
                    estoquista.avaliar(0.1f);
                    System.out.println("  Estoquista " + estoquista.obterNome()
                            + " foi severamente penalizado (avaliação muito baixa)");
                }
                if (gerente != null) {
                    gerente.avaliar(0.2f);
                    System.out.println("  Gerente " + gerente.obterNome() + " foi penalizado (avaliação baixa)");
                }
                continue;
            }

            if (cliente.pagar(precoItem)) {
                restaurante.processarPagamento(precoItem);
                receitaDia += precoItem;

                vendasItens.put(nomeItem, vendasItens.getOrDefault(nomeItem, 0) + 1);

                System.out.println("  ✅ Pagamento processado: R$ " + String.format("%.2f", precoItem));
                System.out.println(
                        "  Dinheiro restante do cliente: R$ " + String.format("%.2f", cliente.obterDinheiro()));
                System.out.println(
                        "  Cofre do restaurante: R$ " + String.format("%.2f", restaurante.obterDinheiroNoCofre()));

                restaurante.obterEstoque().removerIngredientes(itemEscolhido.obterAlimento().obterIngredientes());

            } else {
                System.out.println("  ❌ ERRO: Falha no pagamento!");
                continue;
            }

            Pedido pedido = new Pedido(cliente, itemEscolhido, caixa);
            if (foiRecomendado) {
                pedido.marcarComoRecomendado();
            }
            pedidos.add(pedido);
            System.out.println(
                    "  ✅ Pedido aceito: " + itemEscolhido.obterNome() + " - R$ " + String.format("%.2f", precoItem));
        }

        System.out.println("\n=== PREPARANDO PEDIDOS ===");

        for (Pedido pedido : pedidos) {
            System.out.println("\nPreparando pedido de " + pedido.obterCliente().obterNome() + ": "
                    + pedido.obterItem().obterNome());
            Funcionario cozinheiro = obterCozinheiroDisponivel();
            if (cozinheiro == null) {
                System.out.println("  ERRO: Nenhum cozinheiro disponível");
                continue;
            }
            pedido.definirCozinheiro(cozinheiro);
            System.out.println("  Preparado pelo cozinheiro: " + cozinheiro.obterNome());

            float avaliacaoAlimento = pedido.obterCliente()
                    .calcularRatingParaAlimento(pedido.obterItem().obterAlimento());

            Random random = new Random();
            float avaliacaoPedido = avaliacaoAlimento;

            float desempenhoCozinheiro = cozinheiro.obterAvaliacao();
            if (desempenhoCozinheiro > 0.7f) {
                avaliacaoPedido += 0.15f;
                System.out.println("  🌟 Cozinheiro experiente melhorou o preparo!");
            } else if (desempenhoCozinheiro < 0.3f) {
                avaliacaoPedido -= 0.15f;
                System.out.println("  ⚠️ Cozinheiro inexperiente prejudicou o preparo");
            }

            float fatorAleatorio = (random.nextFloat() - 0.5f) * 0.2f;
            avaliacaoPedido += fatorAleatorio;

            if (pedido.foiRecomendado()) {
                float qualidadeRecomendacao = pedido.obterCaixa().obterAvaliacao();
                if (qualidadeRecomendacao > 0.6f && avaliacaoAlimento > 0.6f) {
                    avaliacaoPedido += 0.1f;
                    System.out.println("  👍 Excelente recomendação do caixa!");
                } else if (qualidadeRecomendacao < 0.4f || avaliacaoAlimento < 0.4f) {
                    avaliacaoPedido -= 0.1f;
                    System.out.println("  👎 Recomendação questionável do caixa");
                }
            }

            avaliacaoPedido = Math.max(0.0f, Math.min(1.0f, avaliacaoPedido));

            System.out.println("  Avaliação base do alimento: " + String.format("%.2f", avaliacaoAlimento));
            System.out.println("  Avaliação final do pedido: " + String.format("%.2f", avaliacaoPedido));

            String nomeItem = pedido.obterItem().obterNome();
            avaliacoesTotaisItens.put(nomeItem,
                    avaliacoesTotaisItens.getOrDefault(nomeItem, 0.0f) + avaliacaoPedido);
            numeroAvaliacoesItens.put(nomeItem,
                    numeroAvaliacoesItens.getOrDefault(nomeItem, 0) + 1);

            pedido.obterItem().avaliar(avaliacaoPedido);

            if (avaliacaoPedido >= 0.8f) {
                System.out.println("  ⭐ EXCELENTE! Cliente ficou muito satisfeito!");
                cozinheiro.avaliar(0.8f);
                if (pedido.foiRecomendado()) {
                    pedido.obterCaixa().avaliar(0.7f);
                }
            } else if (avaliacaoPedido >= 0.6f) {
                System.out.println("  ✅ BOM! Cliente ficou satisfeito!");
                cozinheiro.avaliar(0.6f);
                if (pedido.foiRecomendado()) {
                    pedido.obterCaixa().avaliar(0.6f);
                }
            } else if (avaliacaoPedido >= 0.4f) {
                System.out.println("  😐 REGULAR. Cliente ficou neutro");
                cozinheiro.avaliar(0.4f);
                if (pedido.foiRecomendado()) {
                    pedido.obterCaixa().avaliar(0.4f);
                }
            } else if (avaliacaoPedido >= 0.2f) {
                System.out.println("  😞 RUIM. Cliente ficou insatisfeito");
                cozinheiro.avaliar(0.2f);
                if (pedido.foiRecomendado()) {
                    pedido.obterCaixa().avaliar(0.2f);
                }
            } else {
                System.out.println("  😡 PÉSSIMO! Cliente ficou muito insatisfeito!");
                cozinheiro.avaliar(0.1f);
                if (pedido.foiRecomendado()) {
                    pedido.obterCaixa().avaliar(0.1f);
                }
            }
        }

        System.out.println("\n=== RESUMO FINANCEIRO DO DIA ===");
        System.out.println("💰 Receita do dia: R$ " + String.format("%.2f", receitaDia));
        System.out
                .println("💳 Dinheiro final no cofre: R$ " + String.format("%.2f", restaurante.obterDinheiroNoCofre()));

        float custosEstimados = 0.0f;
        for (Pedido pedido : pedidos) {
            custosEstimados += pedido.obterItem().obterCustoDeProducao();
        }
        System.out.println("💸 Custos estimados de produção: R$ " + String.format("%.2f", custosEstimados));
        System.out.println("📊 Lucro estimado do dia: R$ " + String.format("%.2f", (receitaDia - custosEstimados)));

        System.out.println("\n=== RESUMO OPERACIONAL DO DIA ===");
        System.out.println("👥 Total de clientes que chegaram: " + clientes.size());
        System.out.println("✅ Total de clientes atendidos: " + pedidos.size());
        System.out.println("❌ Clientes que saíram sem pedido: " + (clientes.size() - pedidos.size()));
        System.out.println("💸 Clientes sem dinheiro suficiente: " + clientesSemDinheiro);
        System.out.println("💰 Clientes que desistiram pelo preço: " + clientesDesistiramPreco);
        System.out.println("📦 Clientes que quiseram item esgotado: " + clientesItemEsgotado);

        float taxaConversao = clientes.size() > 0 ? ((float) pedidos.size() / clientes.size()) * 100 : 0;
        System.out.println("📈 Taxa de conversão: " + String.format("%.1f", taxaConversao) + "%");

        System.out.println("\n=== ANÁLISE DE DEMANDA DO DIA ===");
        for (String item : demandasItens.keySet()) {
            int demanda = demandasItens.get(item);
            int vendas = vendasItens.get(item);
            if (demanda > 0 || vendas > 0) {
                System.out.println("📊 " + item + ": " + demanda + " interessados, " + vendas + " vendas");
            }
        }

        System.out.println("\n=== ANÁLISE DE DESEMPENHO DOS ITENS ===");
        for (String item : numeroAvaliacoesItens.keySet()) {
            int numeroAvaliacoes = numeroAvaliacoesItens.get(item);
            if (numeroAvaliacoes > 0) {
                float avaliacaoMedia = calcularAvaliacaoMedia(item);
                String emoji = avaliacaoMedia >= 0.7f ? "⭐"
                        : avaliacaoMedia >= 0.5f ? "👍" : avaliacaoMedia >= 0.3f ? "😐" : "👎";

                System.out.println(emoji + " " + item + ": " +
                        String.format("%.2f", avaliacaoMedia) +
                        " (" + numeroAvaliacoes + " avaliações)");
            }
        }

        System.out.println("\n=== AVALIAÇÕES DOS FUNCIONÁRIOS ===");
        for (Funcionario funcionario : restaurante.obterFuncionarios()) {
            String emoji = funcionario.desempenhoEstaAlto() ? "🟢" : funcionario.desempenhoEstaBaixo() ? "🔴" : "🟡";
            System.out.println(emoji + " " + funcionario.obterCargo() + " " + funcionario.obterNome() +
                    ": " + String.format("%.2f", funcionario.obterAvaliacao()));
        }

        System.out.println("\n=== ALERTAS ===");
        if (restaurante.obterDinheiroNoCofre() < 100.0f) {
            System.out.println("⚠️ ALERTA: Dinheiro no cofre está baixo! Considere reduzir gastos.");
        }
        if (clientesSemDinheiro > 3) {
            System.out.println("⚠️ ALERTA: Muitos clientes sem dinheiro. Considere reduzir preços.");
        }
        if (clientesDesistiramPreco > 3) {
            System.out.println("⚠️ ALERTA: Muitos clientes desistiram pelo preço. Considere ajustar preços.");
        }
        if (clientesItemEsgotado > 2) {
            System.out.println("⚠️ ALERTA: Muitos itens esgotados. Melhore o controle de estoque.");
        }
        if (taxaConversao < 50.0f) {
            System.out.println("⚠️ ALERTA: Taxa de conversão baixa. Revise operações.");
        }

        System.out.println("\n=== FIM DA SIMULAÇÃO ===\n");
    }
/**
 * Processa o pagamento semanal de salários dos funcionários, calculando
 * o total baseado nos cargos e verificando se há recursos suficientes
 * no cofre do restaurante.
 */
    private void pagarSalarios() {
        System.out.println("\n=== 💸 PAGAMENTO DE SALÁRIOS (DIA " + diasDecorridos + ") ===");
        float totalSalarios = 0.0f;

        for (Funcionario funcionario : restaurante.obterFuncionarios()) {
            float salario = calcularSalario(funcionario.obterCargo());
            totalSalarios += salario;

            System.out.println("💰 " + funcionario.obterCargo() + " " + funcionario.obterNome() +
                    ": R$ " + String.format("%.2f", salario));
        }

        System.out.println("📊 Total de salários: R$ " + String.format("%.2f", totalSalarios));
        System.out.println("💳 Dinheiro antes: R$ " + String.format("%.2f", restaurante.obterDinheiroNoCofre()));

        if (restaurante.removerDinheiroNoCofre(totalSalarios)) {
            System.out.println("✅ Salários pagos com sucesso!");
            System.out.println(
                    "💳 Dinheiro após pagamento: R$ " + String.format("%.2f", restaurante.obterDinheiroNoCofre()));
        } else {
            System.out.println("❌ ERRO: Dinheiro insuficiente para pagar todos os salários!");
            System.out.println(
                    "💳 Déficit: R$ " + String.format("%.2f", (totalSalarios - restaurante.obterDinheiroNoCofre())));
            System.out.println("⚠️ CRISE FINANCEIRA DETECTADA!");

            Funcionario gerente = obterGerente();
            if (gerente != null) {
                gerente.avaliar(0.1f);
                System.out.println(
                        "📉 Gerente " + gerente.obterNome() + " foi severamente penalizado pela crise financeira");
            }
        }
        System.out.println("=== FIM DO PAGAMENTO DE SALÁRIOS ===\n");
    }

/**
 * Calcula o salário semanal baseado no cargo do funcionário, implementando
 * a hierarquia salarial da empresa (Gerente > Cozinheiro > Caixa > Estoquista > Faxineiro).
 * 
 * paramentro: cargo Cargo do funcionário
 * return: float Valor do salário semanal em reais
 */
    private float calcularSalario(Cargo cargo) {
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

/**
 * Busca o funcionário mais qualificado para promoção emergencial a gerente
 * quando não há gerente no restaurante, seguindo ordem de prioridade por cargo
 * e avaliação de desempenho.
 * 
 * return: Funcionario Melhor candidato para promoção ou null se não houver
 */
    private Funcionario encontrarMelhorCandidatoParaGerente() {
        Funcionario melhorCandidato = null;
        float melhorAvaliacao = -1.0f;

        Cargo[] ordemPrioridade = { Cargo.Cozinheiro, Cargo.Caixa, Cargo.Estoquista, Cargo.Faxineiro };

        for (Cargo cargoPreferido : ordemPrioridade) {
            for (Funcionario funcionario : restaurante.obterFuncionarios()) {
                if (funcionario.obterCargo() == cargoPreferido) {
                    float avaliacao = funcionario.obterAvaliacao();

                    if (avaliacao > melhorAvaliacao) {
                        melhorCandidato = funcionario;
                        melhorAvaliacao = avaliacao;
                    }

                    if (avaliacao >= 0.4f) {
                        return funcionario;
                    }
                }
            }

            if (melhorCandidato != null && melhorCandidato.obterCargo() == cargoPreferido) {
                return melhorCandidato;
            }
        }

        return melhorCandidato;
    }

/**
 * Localiza e retorna o primeiro funcionário com cargo de Estoquista
 * encontrado na lista de funcionários do restaurante.
 * 
 * return: Funcionario Estoquista encontrado ou null se não houver
 */
    private Funcionario obterEstoquista() {
        for (Funcionario funcionario : restaurante.obterFuncionarios()) {
            if (funcionario.obterCargo() == Cargo.Estoquista) {
                return funcionario;
            }
        }
        return null;
    }
/**
 * Localiza e retorna o funcionário com cargo de Gerente do restaurante.
 * Usado para verificar se há gerência ativa no estabelecimento.
 * 
 * return: Funcionario Gerente encontrado ou null se não houver
 */
    private Funcionario obterGerente() {
        for (Funcionario funcionario : restaurante.obterFuncionarios()) {
            if (funcionario.obterCargo() == Cargo.Gerente) {
                return funcionario;
            }
        }
        return null;
    }

/**
 * Gera uma quantidade aleatória de clientes (entre 40-80) com perfis
 * diversificados incluindo diferentes classes sociais, idades e capacidade
 * financeira para simular o movimento diário do restaurante.
 * 
 * return: ArrayList<Cliente> Lista de clientes gerados aleatoriamente
 */
    private ArrayList<Cliente> gerarClientesRandomicos() {
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        Random random = new Random();

        ClasseSocial[] classesSociais = { ClasseSocial.BaixaRenda, ClasseSocial.MediaRenda, ClasseSocial.AltaRenda };
        String[] nomes = { "Ana", "Bruno", "Carla", "Diego", "Elena", "Felipe", "Giselle", "Hugo", "Iris", "João" };

        for (int i = 0; i < 40 + random.nextInt(40); i++) {
            String nome = nomes[i % nomes.length] + (i / nomes.length > 0 ? " " + (char) ('A' + i / nomes.length) : "");
            int idade = 18 + random.nextInt(50);
            ClasseSocial classeSocial = classesSociais[random.nextInt(classesSociais.length)];

            float dinheiroCliente;
            switch (classeSocial) {
                case AltaRenda:
                    dinheiroCliente = 80.0f + random.nextFloat() * 120.0f;
                    break;
                case MediaRenda:
                    dinheiroCliente = 40.0f + random.nextFloat() * 60.0f;
                    break;
                case BaixaRenda:
                default:
                    dinheiroCliente = 15.0f + random.nextFloat() * 35.0f;
                    break;
            }

            Cliente cliente = new Cliente(nome, idade, classeSocial, dinheiroCliente);
            clientes.add(cliente);
        }

        return clientes;
    }

/**
 * Localiza e retorna o primeiro funcionário com cargo de Caixa disponível
 * para atender clientes durante a simulação do dia.
 * 
 * return: Funcionario Caixa disponível ou null se não houver
 */

    private Funcionario obterCaixaDisponivel() {
        for (Funcionario funcionario : restaurante.obterFuncionarios()) {
            if (funcionario.obterCargo() == Cargo.Caixa) {
                return funcionario;
            }
        }
        return null;
    }
/**
 * Localiza e retorna o primeiro funcionário com cargo de Cozinheiro disponível
 * para preparar os pedidos durante a simulação.
 * 
 * return: Funcionario Cozinheiro disponível ou null se não houver
 */
    private Funcionario obterCozinheiroDisponivel() {
        for (Funcionario funcionario : restaurante.obterFuncionarios()) {
            if (funcionario.obterCargo() == Cargo.Cozinheiro) {
                return funcionario;
            }
        }
        return null;
    }

/**
 * Seleciona aleatoriamente um item do cardápio disponível, simulando
 * a escolha espontânea de um cliente sem influência do atendente.
 * 
 * return: ItemCardapio Item escolhido aleatoriamente ou null se cardápio vazio
 */
    private ItemCardapio escolherItemAleatorio() {
        ArrayList<ItemCardapio> items = restaurante.obteCardapio().obterItemsNoCardapio();
        if (items.isEmpty())
            return null;

        Random random = new Random();
        return items.get(random.nextInt(items.size()));
    }

/**
 * Determina o próximo cargo na hierarquia de promoção baseado no cargo atual,
 * seguindo a ordem: Faxineiro → Estoquista → Caixa → Cozinheiro → Gerente.
 * 
 * paramentro: cargoAtual Cargo atual do funcionário
 * return: Cargo Próximo cargo na hierarquia ou null se já for o mais alto
 */
    private Cargo obterCargoSuperior(Cargo cargoAtual) {
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

/**
 * Determina o cargo inferior na hierarquia para rebaixamentos, seguindo
 * a ordem inversa: Gerente → Cozinheiro → Caixa → Estoquista → Faxineiro.
 * 
 * paramentros: cargoAtual Cargo atual do funcionário
 * return: Cargo Cargo inferior na hierarquia ou null se já for o mais baixo
 */
    private Cargo obterCargoInferior(Cargo cargoAtual) {
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
}