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

export default function Home() {
  const { user, logout } = useAuth();
  const navigate         = useNavigate();
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const [produtos, setProdutos]       = useState([]);

  useEffect(() => {
    api.get('/produtos')
       .then(res => setProdutos(res.data))
       .catch(err => console.error(err));
  }, []);

  const handleLogout = () => {
    logout();
    navigate('/login', { replace: true });
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
            <Link to={`/clientes/${user.detalhes.idUsuario}`}>Minha Conta</Link>
            <Link to="/meus-pedidos">Pedidos</Link>
            <Link to="/favoritos">Produtos Favoritos</Link>
            <Link to="/meus-dados">Meus Dados</Link>
            <Link to="/notificacoes">Notificações</Link>
            <button className={styles.logoutButton} onClick={handleLogout}>
              Sair
            </button>
          </>
        )}

        {user?.tipo === 'admin' && (
          <>
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

        <section className={styles.productsGrid}>
          {produtos.map(p => (
            <div key={p.id_produto} className={styles.productCard}>
              <h3>{p.nome}</h3>
              <p>R$ {Number(p.preco).toFixed(2)}</p>
            </div>
          ))}
        </section>
      </main>

      <footer className={styles.footer}>
        © 2025 TechWave. Todos os direitos reservados.
      </footer>
    </>
  );
}
