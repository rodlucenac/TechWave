import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import Login from './pages/Login';
import Cadastro from './pages/Cadastro';
import Home from './pages/Home';
import ProductsList from './pages/ProductsList';
import ProductForm from './pages/ProductForm';
import CategoryForm from './pages/CategoryForm';
import CategoriesList from './pages/CategoriesList';
import AdminDashboard from './pages/AdminDashboard';
import { CartProvider } from './contexts/CartContext';
import Cart from './pages/Cart';
import Checkout from './pages/Checkout';
import Pagamento from './pages/Pagamento';

function App() {
  return (
    <CartProvider>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/cadastro" element={<Cadastro />} />
        <Route path="/" element={<Home />} />
        <Route path="/clientes/:idUsuario" element={<Home />} />
        <Route path="/admin/:idUsuario" element={<Home />} />

        <Route path="/produtos" element={<ProductsList />} />
        <Route path="/produtos/novo" element={<ProductForm />} />
        <Route path="/produtos/:id/editar" element={<ProductForm />} />
        

        <Route path="/categorias" element={<CategoriesList />} />
        <Route path="/categorias/novo" element={<CategoryForm />} />
        <Route path="/categorias/:id/editar" element={<CategoryForm />} />

        <Route path="/admin-dashboard" element={<AdminDashboard />} />

        {/* Carrinho */}
        <Route path="/cart" element={<Cart />} />
        {/* Checkout */}
        <Route path="/checkout" element={<Checkout />} />
        <Route path="/pagamento" element={<Pagamento />} />

        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>
    </CartProvider>
  );
}

export default App;
