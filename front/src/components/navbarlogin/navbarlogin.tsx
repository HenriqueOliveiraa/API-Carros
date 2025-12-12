// NavbarLogin.tsx
'use client';

import React, { useState } from 'react';
import {
  Sidebar,
  Logo,
  LogoImage,
  LogoText,
  MenuItem,
  MenuIcon,
  MenuText,
  Divider,
  Spacer,
  LogoutButton,
} from './styles';
import {
  FaCar,
  FaRocket,
  FaCalendarAlt,
  FaHeart,
  FaFileContract,
  FaCalculator,
  FaTags,
  FaCog,
  FaSignOutAlt,
} from 'react-icons/fa';

const NavbarLogin = () => {
  const [selectedMenu, setSelectedMenu] = useState('carros');

  const menuItems = [
    { id: 'carros', icon: FaCar, text: 'Destaques' },
    { id: 'lancamentos', icon: FaRocket, text: 'Lançamentos' },
    { id: 'agendamento', icon: FaCalendarAlt, text: 'Agendar Visita' },
    { id: 'favoritos', icon: FaHeart, text: 'Meus Favoritos' },
    { id: 'simulacao', icon: FaCalculator, text: 'Simulação' },
    { id: 'ofertas', icon: FaTags, text: 'Ofertas Especiais' },
    { id: 'propostas', icon: FaFileContract, text: 'Minhas Propostas' },
    { id: 'configuracoes', icon: FaCog, text: 'Configurações' },
  ];

  return (
    <Sidebar>
      <Logo>
        <LogoImage src="/img/logo6.png" alt="Logo" />
        <LogoText>Maria Eduarda</LogoText>
      </Logo>

      <Divider />

      {menuItems.map(item => (
        <MenuItem
          key={item.id}
          active={selectedMenu === item.id}
          onClick={() => setSelectedMenu(item.id)}
        >
          <MenuIcon active={selectedMenu === item.id}>
            <item.icon />
          </MenuIcon>
          <MenuText active={selectedMenu === item.id}>{item.text}</MenuText>
        </MenuItem>
      ))}

      <Spacer />

      <Divider />

      <LogoutButton>
        <MenuIcon>
          <FaSignOutAlt />
        </MenuIcon>
        <MenuText>Sair da conta</MenuText>
      </LogoutButton>
    </Sidebar>
  );
};

export default NavbarLogin;
