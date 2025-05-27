import React, { useState, useEffect, useContext } from 'react';
import { Link } from 'react-router-dom';
import Slider from 'react-slick';
import api from '../services/api';
import Header from '../components/Header';
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import styles from './Home.module.css';
import { AuthContext } from '../contexts/AuthContext';

export default function Home() {
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const [produtos, setProdutos]       = useState([]);
  const { user } = useContext(AuthContext);
  const tipoUsuario = user?.tipo;

  useEffect(() => {
    api.get('/produtos')
       .then(res => setProdutos(res.data))
       .catch(err => console.error(err));
       
  }, []);
  
  console.log('usuario', tipoUsuario, user);
  const promos = [
    '/promos/perfil.jpg',
    '/promos/perfil.jpg',
    '/promos/perfil.jpg',
    '/promos/perfil.jpg'
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
    adaptiveHeight: true
  };

  return (
    <>
      <Header />

      <button 
        className={`${styles.menuButton} ${sidebarOpen ? styles.menuButtonShifted : ''}`} 
        onClick={() => setSidebarOpen(o => !o)}
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
        {tipoUsuario === 'cliente' && (
          <>
            {user?.nome && <p className={styles.sidebarUser}>Olá, {user.nome}</p>}
            <Link to="/meu-perfil">Minha Conta</Link>
            <Link to="/meus-pedidos">Meus Pedidos</Link>
            <Link to="/meus-produtos">Meus Produtos</Link>
            <Link to="/carrinho">Carrinho</Link>
            <Link to="/favoritos">Favoritos</Link>
            <Link to="/notificacoes">Notificações</Link>
          </>
        )}
        {tipoUsuario === 'admin' && (
          <>
            {user?.nome && <p className={styles.sidebarUser}>Administrador: {user.nome}</p>}
            <Link to="/produtos">Lista de Produtos</Link>
            <Link to="/produtos/novo">Adicionar Produto</Link>
            <Link to="/categorias">Lista de Categorias</Link>
            <Link to="/categorias/novo">Adicionar Categoria</Link>
          </>
        )}
        {user && (
          <button onClick={() => {
            localStorage.clear();
            window.location.reload();
          }}>Sair</button>
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
