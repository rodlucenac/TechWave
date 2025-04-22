import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Slider from 'react-slick';
import api from '../services/api';
import Header from '../components/Header';
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import styles from './Home.module.css';

export default function Home() {
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const [produtos, setProdutos]       = useState([]);
  const user = JSON.parse(localStorage.getItem('user')); 

  useEffect(() => {
    api.get('/produtos')
       .then(res => setProdutos(res.data))
       .catch(err => console.error(err));
  }, []);

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

      {/* Botão do menu que se desloca junto com a sidebar */}
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
            <Link to="/cadastro">Cadastrar‑se</Link>
          </>
        )}
        {user && user.role === 'CLIENT' && (
          <>
            <Link to="/meu-perfil">Minha Conta</Link>
            <Link to="/meus-pedidos">Meus Pedidos</Link>
            <Link to="/favoritos">Favoritos</Link>
            <Link to="/notificacoes">Notificações</Link>
          </>
        )}
        {user && user.role === 'ADMIN' && (
          <>
            <Link to="/produtos">Lista de Produtos</Link>
            <Link to="/produtos/novo">Adicionar Produto</Link>
            <Link to="/categorias">Lista de Categorias</Link>
            <Link to="/categorias/novo">Adicionar Categoria</Link>
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
