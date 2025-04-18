import React from 'react';
import Header from '../components/Header';
import styles from './Home.module.css';

export default function Home() {
  return (
    <>
      <Header />

      <div className={styles.container}>
        {/* Sidebar */}
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

        {/* Conteúdo principal */}
        <main className={styles.main}>
          {/* Sub‑header da página */}
          <div className={styles.pageHeader}>Bem‑vindo ao TechWave!</div>

          {/* Carousel (placeholder) */}
          <div className={styles.carousel}>
            Carousel de Destaques
          </div>

          {/* Seção de produtos (placeholder) */}
          <section className={styles.products}>
            {[...Array(6)].map((_, i) => (
              <div key={i} className={styles.productCard}>
                Produto {i + 1}
              </div>
            ))}
          </section>
        </main>
      </div>

      {/* Footer simples */}
      <footer className={styles.footer}>
        © 2025 TechWave. Todos os direitos reservados.
      </footer>
    </>
  );
}
