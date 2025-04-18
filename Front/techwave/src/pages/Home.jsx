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
  const [produtos, setProdutos] = useState([]);

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
    speed: 600,
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

      <div className={styles.container}>
        <div className={`${styles.page} ${sidebarOpen ? styles.sidebarOpen : ''}`}>
          <button
            className={styles.toggleButton}
            onClick={() => setSidebarOpen(o => !o)}
          >
            {sidebarOpen ? 'x' : '☰'}
          </button>

          <aside className={styles.sidebar}>
            <nav>
              <ul className={styles.menu}>
                <li className={styles.menuItem}>Minha Conta</li>
                <li className={styles.menuItem}>Meus Dados</li>
                <li className={styles.menuItem}>Meus Pedidos</li>
                <li className={styles.menuItem}>Favoritos</li>
                <li className={styles.menuItem}>Notificações</li>
              </ul>
            </nav>
          </aside>

          <main className={styles.main}>

            <div className={styles.carouselWrapper}>
              <Slider {...sliderSettings} className={styles.carousel}>
                {promos.map((url, idx) => (
                  <div key={idx} className={styles.slide}>
                    <img
                      src={url}
                      alt={`Promoção ${idx + 1}`}
                      className={styles.slideImage}
                    />
                  </div>
                ))}
              </Slider>
            </div>

            <section className={styles.products}>
              {produtos.map(p => (
                <div key={p.id_produto} className={styles.productCard}>
                  <Link to={`/produto/${p.id_produto}`}>
                    <img
                      src={`/images/${p.imagem_nome}`}
                      alt={p.nome}
                      className={styles.productImage}
                    />
                  </Link>
                  <div className={styles.productInfo}>
                    <h3 className={styles.productName}>{p.nome}</h3>
                    <p className={styles.productPrice}>
                      R$ {Number(p.preco).toFixed(2)}
                    </p>
                  </div>
                </div>
              ))}
            </section>
          </main>
        </div>

        <footer className={styles.footer}>
          © 2025 TechWave. Todos os direitos reservados.
        </footer>
      </div>
    </>
  );
}
