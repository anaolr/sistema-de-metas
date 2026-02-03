<h1 align="center"> Sistema de Metas Corporativas</h1>

<p align="center">
Aplicação desktop desenvolvida em Java para gerenciamento de metas, desempenho e premiações em ambientes corporativos
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white" />
  <img src="https://img.shields.io/badge/Java_Swing-007396?style=for-the-badge&logo=java&logoColor=white" />
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white" />
  <img src="https://img.shields.io/badge/JDBC-0052CC?style=for-the-badge&logo=java&logoColor=white" />
  <img src="https://img.shields.io/badge/Status-Concluído-2ECC71?style=for-the-badge" />
</p>

---

##  Visão Geral

O **Sistema de Metas Corporativas** é uma aplicação desktop desenvolvida em **Java**, com foco no controle estratégico de metas, acompanhamento de desempenho e incentivo por meio de premiações.

O sistema simula um **ambiente corporativo real**, organizando usuários por **níveis hierárquicos** e aplicando **regras de negócio**, como aprovação de metas, controle de acesso por perfil e histórico de desempenho.

Este projeto foi desenvolvido com o objetivo de **praticar lógica de programação, arquitetura em camadas, integração com banco de dados relacional e fluxo de sistemas empresariais**.

---

##  Perfis de Usuário e Funcionalidades

###  Colaborador
- Visualiza apenas suas próprias metas  
- Acompanha o status das metas (pendente, em andamento, concluída, aprovada ou reprovada)  
- Envia metas concluídas para aprovação  
- Visualiza premiações recebidas  

---

###  Supervisor
- Gerencia colaboradores do seu departamento  
- Atribui metas individuais  
- Avalia metas enviadas para aprovação  
- Aprova ou reprova metas com justificativa  
- Acompanha o desempenho geral do time  

---

###  RH (Administrador)
- Cadastra colaboradores e departamentos  
- Gerencia metas corporativas  
- Visualiza históricos completos da empresa  
- Possui acesso total ao sistema  

---

##  Fluxo de Funcionamento

1. O RH cadastra colaboradores e departamentos  
2. O supervisor atribui metas aos colaboradores  
3. O colaborador executa e envia a meta para aprovação  
4. O supervisor aprova ou reprova a meta  
5. Após aprovação, a premiação é liberada  
6. O RH acompanha métricas e históricos  

---

##  Arquitetura do Sistema

1. `conexao/` → Conexão com o banco de dados  
2. `dao/` → Acesso a dados (DAO + JDBC)  
3. `modelos/` → Entidades e regras de domínio  
4. `telas/` → Interface gráfica (Java Swing)  
5. `lib/` → Dependências do projeto  

O projeto segue uma organização inspirada no padrão **MVC com DAO**, comum em aplicações Java corporativas.

---

##  Banco de Dados

- **MySQL**  
- Conexão via **JDBC**  
- Criação automática de:
  - Banco de dados
  - Tabelas
  - Triggers  
- Uso de **triggers** para manter histórico de colaboradores desativados  
- Inserção automática de dados iniciais (seed)  

---

## Interface Gráfica

- Desenvolvida com **Java Swing**  
- Telas específicas para cada perfil de usuário  
- Eventos integrados ao banco de dados  
- Atualização dinâmica de informações  

---

## Preview do Sistema

<div align="center">

<img src="https://github.com/user-attachments/assets/afc98d69-18b1-4af8-a697-536b12809c2c" width="400" />
<img src="https://github.com/user-attachments/assets/342de02b-0c65-4166-9abd-58a771d5b5df" width="400" />
<img src="https://github.com/user-attachments/assets/faf5199b-54bf-4ce2-b3c1-d5bbcb02bcaa" width="400" />
<img src="https://github.com/user-attachments/assets/20dc52d1-8c48-4caa-ba25-af3be4454051" width="400" />

</div>

---

##  Tecnologias Utilizadas

- Java  
- Java Swing  
- MySQL  
- JDBC  

---

##  Aprendizados com o Projeto

- Organização de código em camadas  
- Integração Java com banco de dados relacional  
- Uso de DAO e JDBC puro  
- Implementação de regras de negócio  
- Criação de triggers e controle de histórico  
- Simulação de sistemas corporativos reais  

---

##  Como Executar

1. Certifique-se de que o MySQL esteja em execução  
2. Configure usuário e senha no arquivo de conexão  
3. Execute a classe principal do projeto  
4. O banco de dados e as tabelas serão criados automaticamente  
