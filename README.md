# TechWave
# 📌 Mini-Mundo - TechWave

## 🛒 Sobre o Projeto
O **TechWave** é um **e-commerce especializado em tecnologia**, oferecendo produtos como **notebooks, hardware, periféricos e acessórios gamer**. O sistema permite que clientes realizem **compras**, adicionando produtos ao **carrinho de compras** e finalizando pedidos com diferentes formas de pagamento.

---

## 🔹 Funcionalidades do Sistema
✔ **Clientes** podem se cadastrar, adicionar produtos ao carrinho e realizar pedidos.  
✔ **Administradores** gerenciam produtos, categorias e acompanham pedidos.  
✔ Cada **produto** pertence a uma **categoria** (ex: "Monitores", "Placas de Vídeo").  
✔ **Clientes possuem um carrinho de compras**, onde podem adicionar vários produtos.  
✔ Um **pedido é gerado a partir do carrinho de compras** e pode conter múltiplos itens.  
✔ O **pagamento** pode ser realizado via **cartão, boleto ou PIX**, e possui um status de aprovação.  
✔ Cada **cliente pode cadastrar múltiplos endereços de entrega**.  
✔ O sistema mantém um histórico dos pedidos de cada cliente.  

---

## 🔹 Regras de Negócio
✔ Cada **cliente** possui um **único carrinho**, mas pode adicionar múltiplos produtos a ele.  
✔ Um **produto pode estar presente em vários carrinhos ao mesmo tempo**.  
✔ Cada **pedido** tem um **pagamento vinculado**, que pode estar "Pendente", "Aprovado" ou "Recusado".  
✔ Um **administrador** tem permissões para adicionar, editar e remover produtos do catálogo.  
✔ Os produtos possuem **imagens associadas**, permitindo um melhor detalhamento visual.  
✔ As categorias podem ser **auto-relacionadas**, permitindo **subcategorias** (exemplo: "Hardware" → "Placas de Vídeo").  

---

## 🔹 Estrutura do Banco de Dados
O banco de dados do **TechWave** foi modelado para garantir **eficiência e escalabilidade**, contemplando:  
✅ **Herança** entre `Usuario`, `Cliente` e `Administrador`.  
✅ **Relacionamento ternário** para o **carrinho de compras** (`Cliente`, `Produto` e `Carrinho`).  
✅ **Entidade fraca** para `Item_do_Pedido`, vinculada a `Pedido`.  
✅ **Atributos compostos** em `Endereco` (Rua, Número, Bairro, Cidade, Estado, CEP).  
✅ **Relacionamento N:N** entre `Pedido` e `Produto` via `Item_do_Pedido`.  

---
