import React, { useEffect, useState } from 'react';
import { getPedidosDoCliente, getDetalhesPedido } from '../services/pedidoService';
import Header from '../components/Header';
import { useAuth } from '../contexts/AuthContext';
import styles from './MeusPedidos.module.css';

export default function MeusPedidos() {
  const [pedidos, setPedidos] = useState([]);
  const [pedidosDetalhados, setPedidosDetalhados] = useState({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [expandedPedido, setExpandedPedido] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  
  // Acesso ao usuário logado via contexto de autenticação
  const { user } = useAuth();
  const clienteId = user?.id || 22; // Use o ID do cliente logado ou 22 como fallback

  useEffect(() => {
    async function fetchPedidos() {
      try {
        setLoading(true);
        const response = await getPedidosDoCliente(clienteId);
        setPedidos(response.data);
      } catch (err) {
        console.error('Erro ao buscar pedidos:', err);
        setError('Não foi possível carregar seus pedidos. Tente novamente mais tarde.');
      } finally {
        setLoading(false);
      }
    }
    
    fetchPedidos();
  }, [clienteId]);

  // Função para buscar os itens de um pedido quando expandido
  const expandPedido = async (pedidoId) => {
    if (expandedPedido === pedidoId) {
      setExpandedPedido(null); // Recolhe se já está expandido
      return;
    }
    
    try {
      // Verifica se já buscamos os detalhes deste pedido antes
      if (!pedidosDetalhados[pedidoId]) {
        const response = await getDetalhesPedido(pedidoId);
        setPedidosDetalhados(prev => ({
          ...prev,
          [pedidoId]: response.data
        }));
      }
      
      setExpandedPedido(pedidoId);
    } catch (err) {
      console.error(`Erro ao buscar detalhes do pedido ${pedidoId}:`, err);
      alert('Não foi possível obter os detalhes deste pedido.');
    }
  };

  // Filtra pedidos com base no termo de pesquisa
  const filteredPedidos = searchTerm 
    ? pedidos.filter(pedido => 
        pedido.idPedido.toString().includes(searchTerm) ||
        // Se tivéssemos acesso aos itens aqui, filtraríamos também por nome de produto
        (pedidosDetalhados[pedido.idPedido]?.itens?.some(
          item => item.nomeProduto?.toLowerCase().includes(searchTerm.toLowerCase())
        ))
      )
    : pedidos;

  // Formata o preço para o formato brasileiro (R$)
  const formatPrice = (price) => {
    return new Intl.NumberFormat('pt-BR', { 
      style: 'currency', 
      currency: 'BRL' 
    }).format(price);
  };

  // Determina a classe CSS com base no status
  const getStatusClass = (status) => {
    status = status?.toLowerCase();
    if (status === 'confirmado' || status === 'entregue' || status === 'concluído') {
      return styles.sucesso;
    } else if (status === 'cancelado' || status === 'recusado') {
      return styles.erro;
    } else {
      return styles.pendente;
    }
  };

  return (
    <>
      <Header />
      <div className={styles.container}>
        <h1 className={styles.title}>🧾 Meus Pedidos</h1>
        
        <div className={styles.searchContainer}>
          <input
            type="text"
            placeholder="Buscar por número do pedido ou produto"
            className={styles.search}
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>

        {loading && <p className={styles.loading}>Carregando seus pedidos...</p>}
        
        {error && <p className={styles.error}>{error}</p>}
        
        {!loading && !error && filteredPedidos.length === 0 && (
          <p className={styles.empty}>Você ainda não possui pedidos.</p>
        )}

        {filteredPedidos.map((pedido) => (
          <div key={pedido.idPedido} className={styles.pedidoBox}>
            <div className={styles.pedidoHeader}>
              <span className={styles.pedidoId}>
                <strong>Pedido:</strong> #{pedido.idPedido} - {new Date(pedido.dataPedido).toLocaleDateString()}
              </span>
              <div className={styles.actions}>
                <button 
                  className={styles.detalhesBtn}
                  onClick={() => expandPedido(pedido.idPedido)}
                >
                  {expandedPedido === pedido.idPedido ? 'OCULTAR DETALHES' : 'VER DETALHES'}
                </button>
              </div>
            </div>
            
            <div className={styles.pedidoInfo}>
              <p className={styles.status}>
                <span className={getStatusClass(pedido.statusPedido)}>
                  Status: {pedido.statusPedido || 'Processando'}
                </span>
              </p>
              <p className={styles.pagamento}>
                💳 Pagamento: <strong>{pedido.statusPagamento || 'Processando'}</strong>
              </p>
              <p className={styles.total}>
                <strong>Total:</strong> {formatPrice(pedido.valorTotal)}
              </p>
            </div>
            
            {expandedPedido === pedido.idPedido && (
              <div className={styles.itemsContainer}>
                <h3>Itens do Pedido</h3>
                
                {!pedidosDetalhados[pedido.idPedido] ? (
                  <p>Carregando itens...</p>
                ) : (
                  <>
                    {pedidosDetalhados[pedido.idPedido].itens?.map((item) => (
                      <div key={item.idProduto} className={styles.itemBox}>
                        <img 
                          src={item.imagemUrl || '/placeholder.png'} 
                          alt={item.nomeProduto} 
                          className={styles.itemImg} 
                        />
                        <div className={styles.itemInfo}>
                          <p className={styles.itemNome}>{item.nomeProduto}</p>
                          <p>Quantidade: {item.quantidade}</p>
                          <p>Preço unitário: {formatPrice(item.precoUnitario)}</p>
                          <p>Subtotal: {formatPrice(item.precoUnitario * item.quantidade)}</p>
                        </div>
                      </div>
                    ))}
                    
                    {pedidosDetalhados[pedido.idPedido].itens?.length === 0 && (
                      <p>Este pedido não possui itens.</p>
                    )}
                  </>
                )}
              </div>
            )}
          </div>
        ))}
      </div>
     
    </>
  );
}
