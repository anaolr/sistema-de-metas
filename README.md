


<h1 align="center">🎯 SISTEMA DE METAS CORPORATIVAS</h1>
<p align="center">Controle estratégico de metas, premiações e desempenho empresarial</p>

---

## 📌 Visão Geral do Sistema

O **Sistema de Metas Corporativas** é uma aplicação desenvolvida em **Java**, projetada para empresas que desejam acompanhar, gerenciar e impulsionar o desempenho de seus colaboradores através de **metas**, **premiações** e **indicadores de produtividade**.

Ele organiza a comunicação entre três níveis hierárquicos:

- **RH** → Administração geral de colaboradores, departamentos e metas  
- **Supervisores** → Gestão do seu departamento e aprovação de metas concluídas  
- **Colaboradores** → Acompanhamento de metas e premiações recebidas  

O objetivo central é **tornar o processo de metas mais transparente, rápido e motivador**, criando um ambiente de resultados e reconhecimento.

---

## 🏛️ Arquitetura de Usuários

### 👥 **1. Colaborador**
- Visualiza suas metas individuais  
- Acompanha o status (pendente, em andamento, concluída, aprovada, reprovada)  
- Confere **premiações** relacionadas às metas concluídas  
- Envia metas concluídas para aprovação do supervisor  

---

### 🧭 **2. Supervisor**
- Gerencia colaboradores do seu próprio departamento  
- Atribui metas individuais  
- Acompanha o **histórico de metas** do departamento  
- Possui uma área exclusiva de **Aprovações**, onde:
  - Recebe metas concluídas por colaboradores  
  - Pode **aprovar ou rejeitar** com justificativa  
- Monitora desempenho geral do time  

---

### 🏢 **3. RH (Recursos Humanos)**
Usuário com **poder administrativo total**:

- Cadastra colaboradores  
- Cadastra departamentos  
- Cadastra metas corporativas  
- Visualiza **todos os históricos** da empresa:
  - Histórico de metas por departamento  
  - Histórico de colaboradores  
- Acompanha indicadores e métricas globais  

Este perfil garante governança e controle sobre todo o sistema.

---

## 🎯 Objetivo Estratégico do Sistema

O sistema foi criado para resolver problemas comuns em empresas:

- 🔸 Falta de organização no acompanhamento de metas  
- 🔸 Falta de transparência entre supervisor e colaborador  
- 🔸 Processos manuais e demorados  
- 🔸 Não incentivo ao atingimento de resultados  

Com esta plataforma:

- 🎉 Colaboradores se sentem motivados por prêmios e reconhecimento  
- 📊 Supervisores têm controle e visão do desempenho do seu time  
- 🏢 RH possui gestão macro, segura e centralizada  

---
## 🖥️ Estrutura Técnica (Resumo)

- **Linguagem:** Java  
- **Banco de Dados: MySQL
- **Interface:** Java Swing  

---
## 📸 **Preview Visual**

<div align="center">

<img src="https://github.com/user-attachments/assets/afc98d69-18b1-4af8-a697-536b12809c2c" />

<img width="876" height="580" alt="Captura de tela 2025-11-14 155149" src="https://github.com/user-attachments/assets/65897b82-fc6e-4ed7-a18b-2dc67753d68e" />

<img width="779" height="485" alt="Captura de tela 2025-11-14 155209" src="https://github.com/user-attachments/assets/ad484ff6-91b3-4818-9841-164d5f11cc09" />

<img width="885" height="583" alt="Captura de tela 2025-11-14 155303" src="https://github.com/user-attachments/assets/342de02b-0c65-4166-9abd-58a771d5b5df" />


<img width="776" height="491" alt="Captura de tela 2025-11-14 155340" src="https://github.com/user-attachments/assets/faf5199b-54bf-4ce2-b3c1-d5bbcb02bcaa" />

<img width="771" height="485" alt="Captura de tela 2025-11-14 155353" src="https://github.com/user-attachments/assets/20dc52d1-8c48-4caa-ba25-af3be4454051" />

---
## 📊 Fluxo Geral do Sistema

```mermaid
flowchart TD
    A[RH cadastra colaborador] --> B[Supervisor atribui metas]
    B --> C[Colaborador executa a meta]
    C --> D[Colaborador envia para aprovação]
    D --> E[Supervisor aprova ou reprova]
    E --> F[Premiação é liberada para o colaborador]
    F --> G[RH acompanha relatórios e histórico]
