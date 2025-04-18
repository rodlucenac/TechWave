import React from 'react';
import styles from './Header.module.css';

export default function Header() {
  return (
    <header className={styles.header}>
      <button className={styles.title}>TechWave</button>
    </header>
  );
}
