// src/pages/Home.jsx
import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import Slider from 'react-slick';
import api from '../services/api';
import Header from '../components/Header';
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import styles from './Home.module.css';
import { useCart } from '../contexts/CartContext';
import { addItem } from '../services/cartService';
import { FaHeart, FaRegHeart } from 'react-icons/fa';


export default function Home() {
  const { user, logout } = useAuth();
  const navigate         = useNavigate();
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const [produtos, setProdutos]       = useState([]);
  const {  ensureCart } = useCart();
  const [favoritos, setFavoritos] = useState([]);

  useEffect(() => {
    api.get('/produtos')
       .then(res => setProdutos(res.data))
       .catch(err => console.error(err));
  }, []);

  const handleLogout = () => {
    logout();
    navigate('/login', { replace: true });
  };

  useEffect(() => {
  async function fetchFavoritos() {
    if (user?.detalhes?.idUsuario) {
      const resp = await api.get('/api/favoritos/listar', {
        params: { clienteId: user.detalhes.idUsuario }
      });
      setFavoritos(resp.data.map(fav => fav.produtoId));
    }
  }
  fetchFavoritos();
  }, [user]);

  const handleAddToCart = async (idProduto) => {
    try {
      const id = await ensureCart();
      await addItem(id, idProduto, 1);
      alert('Produto adicionado ao carrinho!');
    } catch (err) {
      console.error(err);
      alert('Erro ao adicionar: ' + err.message);
    }
  };

  const handleToggleFavorito = async (idProduto) => {
    if (!user) {
      alert('Faça login para favoritar produtos!');
      navigate('/login');
      return;
    }
    const jaFavoritado = favoritos.includes(idProduto);
    try {
      if (!jaFavoritado) {
        console.log(user);
        await api.post('/api/favoritos/adicionar', null, {
          params: { produtoId: idProduto, clienteId: user.detalhes.id }
        });
        setFavoritos(favs => [...favs, idProduto]);
      } else {
        await api.delete('/api/favoritos/remover', {
          params: { produtoId: idProduto, clienteId: user.detalhes.id }
        });
        setFavoritos(favs => favs.filter(id => id !== idProduto));
      }
    } catch (e) {
      alert('Erro ao favoritar!');
    }
  };

  const promos = [
    '/promos/perfil.jpg',
    '/promos/perfil.jpg',
    '/promos/perfil.jpg',
    '/promos/perfil.jpg',
  ];
  const sliderSettings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 4000,
    arrows: true,
    adaptiveHeight: true,
  };

  return (
    <>
      <Header />

      <button
        className={`${styles.menuButton} ${sidebarOpen ? styles.menuButtonShifted : ''}`}
        onClick={() => setSidebarOpen(open => !open)}
      >
        {sidebarOpen ? '✕' : '☰'}
      </button>

      <aside className={`${styles.sidebar} ${sidebarOpen ? styles.open : ''}`}>
        {!user && (
          <>
            <Link to="/login">Login</Link>
            <Link to="/cadastro">Cadastre-se</Link>
          </>
        )}

        {user?.tipo === 'cliente' && (
          <>
            <p className={styles.userGreeting}>Olá, {user.detalhes.nome}!</p>
            <Link to="/meus-pedidos">Pedidos</Link>
            <Link to="/favorito">Produtos Favoritos</Link>
            <Link to="/meus-dados">Meus Dados</Link>
            <Link to="/enderecos">Endereço</Link>
            <Link to="/notificacoes">Notificações</Link>
            <button className={styles.logoutButton} onClick={handleLogout}>
              Sair
            </button>
          </>
        )}

        {user?.tipo === 'admin' && (
          <>
            <p className={styles.userGreeting}>Olá, {user.detalhes.nome}!</p>
            <Link to={`/admin/${user.detalhes.idUsuario}`}>Minha Conta</Link>
            <Link to="/produtos">Gerenciar Produtos</Link>
            <Link to="/categorias">Gerenciar Categorias</Link>
            <Link to={`/admin/${user.detalhes.idUsuario}`}>Dashboards</Link>
            <button className={styles.logoutButton} onClick={handleLogout}>
              Sair
            </button>
          </>
        )}
      </aside>

      <main className={`${styles.mainContent} ${sidebarOpen ? styles.shifted : ''}`}>
        <div className={styles.cartButtonWrapper}>
          <Link to="/cart" className={styles.cartButton}>
            Ver Carrinho
          </Link>
        </div>
        <div className={styles.sliderWrapper}>
          <Slider {...sliderSettings}>
            {promos.map((url, idx) => (
              <div key={idx}>
                <img
                  src={url}
                  alt={`Promoção ${idx + 1}`}
                  className={styles.promoImage}
                />
              </div>
            ))}
          </Slider>
        </div>

        {produtos.map(p => (
          <div key={p.idProduto} className={styles.productCard}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
              <h3>{p.nome}</h3>
              <button
                style={{ background: 'none', border: 'none', cursor: 'pointer', color: '#c00', fontSize: 22 }}
                onClick={() => handleToggleFavorito(p.idProduto)}
                aria-label={favoritos.includes(p.idProduto) ? 'Desfavoritar' : 'Favoritar'}
              >
                {favoritos.includes(p.idProduto) ? <FaHeart /> : <FaRegHeart />}
              </button>
            </div>
            <p>R$ {Number(p.preco).toFixed(2)}</p>
            <button
              className={styles.btnAddCart}
              onClick={() => handleAddToCart(p.idProduto)}
            >
              Adicionar ao Carrinho
            </button>
          </div>
        ))}

      </main>

      <footer className={styles.footer}>
        © 2025 TechWave. Todos os direitos reservados.
      </footer>
    </>
  );
}
