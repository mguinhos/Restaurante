# Restaurante

Este projeto é um modelo de **restaurante** implementado em **Java 21**, desenvolvido para a disciplina de **POO (Programação Orientada a Objetos)** da **UNICAP (2025)**.  

O sistema oferece uma **interface de linha de comando** para interação com mesas, reservas, clientes, funcionários e cardápio, simulando o funcionamento básico de um restaurante.  

## Funcionalidades

- Gerenciamento de **mesas** e verificação de disponibilidade.  
- Realização de **reservas** para clientes.  
- Cadastro de **clientes** e **funcionários**.  
- Manipulação de um **cardápio** de alimentos.  
- Interface de **linha de comando** simples para interação com o usuário.

## Tecnologias

- **Java 21**  
- **Makefile** para build e execução  
- Estrutura orientada a **POO (Programação Orientada a Objetos)**

## Instalação

### Debian / Ubuntu

1. Atualize os repositórios:

```bash
sudo apt update
````

2. Instale o OpenJDK 21:

```bash
sudo apt install openjdk-21-jdk
```

3. Verifique a instalação do Java:

```bash
java -version
```

4. Instale o GNU Make:

```bash
sudo apt install make
```

5. Verifique a instalação do Make:

```bash
make --version
```

### Windows

1. Baixe o **OpenJDK 21** de um distribuidor confiável, como [Adoptium](https://adoptium.net/) ou baixe a versão não livre no site da ORACLE.
2. Instale o JDK seguindo o instalador do Windows.
3. Configure a variável de ambiente `JAVA_HOME` apontando para a pasta do JDK, por exemplo:

```
C:\Program Files\Java\jdk-21
```

4. Adicione `%JAVA_HOME%\bin` ao PATH do sistema.
5. Verifique a instalação do Java no Prompt de Comando:

```cmd
java -version
```

6. Instale o **GNU Make** no Windows:

   * Uma opção é instalar o **GnuWin32 Make**: [http://gnuwin32.sourceforge.net/packages/make.htm](http://gnuwin32.sourceforge.net/packages/make.htm)
   * Ou instalar através do **Chocolatey**:

```cmd
winget install make
```

7. Verifique a instalação do Make:

```cmd
make --version
```


## Build e Execução

O projeto utiliza um **Makefile** para compilar e executar:

```bash
# Compilar
make build

# Executar
make run
```

> Certifique-se de ter o **Java 21** instalado no seu sistema.

## Estrutura do Projeto

* `restaurante/` - Contém todas as classes Java do sistema.
* `Makefile` - Comandos para build e execução do projeto.
* `LICENSE.md` - Licença do projeto.

## Licença

Este projeto está licenciado sob a **MIT License**. Consulte o arquivo [LICENSE.md](LICENSE.md) para mais informações.

## Autores

- Marcel Guinhos
- Lucas Soares
- Inaldo Neto

---

2025 - Marcel Guinhos - UNICAP