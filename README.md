# 🛒 TechWave - E-commerce de Tecnologia  

## 📌 Sobre o Projeto  
O TechWave é um e-commerce especializado em tecnologia, oferecendo produtos como notebooks, hardware, periféricos e acessórios gamer.  
A plataforma permite que clientes realizem compras, adicionando produtos ao carrinho de compras e finalizando pedidos com diferentes formas de pagamento.  

---

## 🔹 Funcionalidades do Sistema  
✔ Clientes podem se cadastrar, adicionar produtos ao carrinho e realizar pedidos.  
✔ Administradores gerenciam produtos, categorias e acompanham pedidos.  
✔ Cada produto pertence a uma categoria (exemplo: "Monitores", "Placas de Vídeo").  
✔ Clientes possuem um carrinho de compras, onde podem adicionar vários produtos.  
✔ Um pedido é gerado a partir do carrinho de compras e pode conter múltiplos itens.  
✔ O pagamento pode ser realizado via cartão de crédito, boleto ou PIX, e possui um status de aprovação.  
✔ Cada cliente pode cadastrar múltiplos endereços de entrega.  
✔ O sistema mantém um histórico dos pedidos de cada cliente.  
✔ **Política de Estoque:** O sistema controla a disponibilidade dos produtos para evitar compras de itens esgotados.  
✔ **Notificações de Status de Pedido:** Clientes recebem notificações sobre mudanças no status do pedido ("Pagamento Confirmado", "Pedido em Processamento", "Pedido Enviado", "Pedido Entregue").  

---

## 🔹 Regras de Negócio  
✔ Cada cliente possui um único carrinho, mas pode adicionar múltiplos produtos a ele.  
✔ Um produto pode estar presente em vários carrinhos ao mesmo tempo.  
✔ Cada pedido tem um pagamento vinculado, que pode estar em um dos seguintes estados:  
  - `"Pendente"`  
  - `"Aprovado"`  
  - `"Recusado"`  
✔ Administradores podem adicionar, editar e remover produtos do catálogo.  
✔ Os produtos possuem imagens associadas, permitindo um melhor detalhamento visual.  
✔ As categorias podem ser auto-relacionadas, permitindo subcategorias (exemplo: "Hardware" → "Placas de Vídeo").  

---

### **📌 Política de Estoque:**  
✔ Produtos possuem um estoque mínimo e um estoque disponível.  
✔ Se um produto estiver sem estoque, ele não pode ser adicionado ao carrinho.  
✔ Caso o estoque acabe enquanto o produto estiver no carrinho, o cliente será notificado para remover o item antes de finalizar a compra.  

---

### **📌 Notificações de Status do Pedido:**  
✔ Clientes recebem notificações automáticas via email e painel do usuário sempre que houver uma atualização no status do pedido.  
✔ Os status possíveis são:  
  - `"Pagamento Confirmado"`  
  - `"Pedido em Processamento"`  
  - `"Pedido Enviado"`  
  - `"Pedido Entregue"`  

---

## 🔹 Estrutura do Banco de Dados  
O banco de dados do **TechWave** foi modelado para garantir eficiência e escalabilidade, contemplando:  

---

✅ **Herança entre `Usuário`, `Cliente` e `Administrador`.**  
✅ **Relacionamento entre `Cliente`, `Produto` e `Carrinho_compra` para gerenciamento das compras.**  
✅ **Atributos compostos em `Endereco` (Rua, Número, Bairro, Cidade, Estado, CEP).**  
✅ **Relacionamento N:N entre `Pedido` e `Produto` com um atributo `quantidade`.**  
✅ **Controle de estoque em `Produto`, garantindo que pedidos sejam realizados apenas para itens disponíveis.**  
✅ **Tabela `Notificação`, vinculada ao Cliente através da tabela `Cliente_Notificacao`, permitindo acompanhar quais notificações foram recebidas e lidas.**  

---

## 📌 Conclusão  
O TechWave foi projetado para proporcionar uma experiência de compra otimizada para clientes, garantindo uma gestão eficiente de produtos e pedidos.  
Com a **Política de Estoque** e o **Sistema de Notificações de Status de Pedido**, o e-commerce se torna mais dinâmico e confiável.  

---

## 🛠️ Tecnologias Utilizadas  
✔ **Linguagem:** Java  
✔ **Banco de Dados:** MySQL  
✔ **Frameworks:** SpringBoot e React.js 

---

## 🌍 Mini-mundo

[Mini-mundo TechWave.pdf](https://github.com/user-attachments/files/19556919/Mini-mundo.TechWave.pdf)

---

##  💡 Modelo conceitual

![Conceitual TechWave](<Conceitual TechWave.png>)

---

## 🧠 Modelo lógico

![Lógico Techwave](<Lógico TechWave.png>)

---
