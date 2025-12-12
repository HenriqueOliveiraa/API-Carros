'use client';

import React, { useState } from 'react';
import {
  Container,
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
  Content,
} from './styles';
import {
  FaUsers,
  FaCar,
  FaChartLine,
  FaFileContract,
  FaCalendarCheck,
  FaCog,
  FaBell,
  FaClipboardList,
  FaSignOutAlt,
} from 'react-icons/fa';

const NavbarAdmin = () => {
  const [selectedMenu, setSelectedMenu] = useState('');

  const menuItems = [
    { id: 'dashboard', icon: FaChartLine, text: 'Dashboard' },
    { id: 'usuarios', icon: FaUsers, text: 'Gerenciar Usuários' },
    { id: 'veiculos', icon: FaCar, text: 'Gerenciar Veículos' },
    { id: 'propostas', icon: FaFileContract, text: 'Propostas Recebidas' },
    { id: 'agendamentos', icon: FaCalendarCheck, text: 'Agendamentos' },
    { id: 'relatorios', icon: FaClipboardList, text: 'Relatórios' },
    { id: 'notificacoes', icon: FaBell, text: 'Notificações' },
    { id: 'configuracoes', icon: FaCog, text: 'Configurações' },
  ];

  return (
    <Container>
      <Sidebar>
        <Logo>
          <LogoImage src="/img/logo6.png" alt="Logo" />
          <LogoText>Administrador</LogoText>
        </Logo>

        <Divider />

        {menuItems.map(item => (
          <MenuItem
            key={item.id}
            active={selectedMenu === item.id}
            onClick={() => setSelectedMenu(item.id)}
          >
            <MenuIcon>
              <item.icon />
            </MenuIcon>
            <MenuText>{item.text}</MenuText>
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

      <Content></Content>
    </Container>
  );
};

export default NavbarAdmin;
