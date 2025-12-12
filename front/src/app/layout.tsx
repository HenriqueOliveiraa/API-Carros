'use client';

import Providers from '@/components/Providers/Providers';
import localFont from 'next/font/local';
import { ThemeProvider } from 'styled-components';
import { theme } from '@/styles/theme';
import { GlobalStyle } from '@/styles/global';
import Navbar from '@/components/Navbar/Navbar';
import { usePathname } from 'next/navigation';
import './globals.css';

// importação de fontes locais
const cabourgOT = localFont({
  src: [
    {
      path: '../fonts/CabourgOT-Bold.otf',
      weight: '700',
      style: 'normal',
    },
    {
      path: '../fonts/CabourgOT-Regular.otf',
      weight: '400',
      style: 'normal',
    },
  ],
  variable: '--font-cabourg',
});

const interVariable = localFont({
  src: [
    {
      path: '../fonts/InterVariable.ttf',
      weight: '100 900',
      style: 'normal',
    },
    {
      path: '../fonts/InterVariable-Italic.ttf',
      weight: '100 900',
      style: 'italic',
    },
  ],
  variable: '--font-inter',
});

const metropolis = localFont({
  src: [
    {
      path: '../fonts/Metropolis-Bold.otf',
      weight: '700',
      style: 'normal',
    },
    {
      path: '../fonts/Metropolis-Regular.otf',
      weight: '400',
      style: 'normal',
    },
    {
      path: '../fonts/Metropolis-SemiBold.otf',
      weight: '600',
      style: 'normal',
    },
  ],
  variable: '--font-metropolis',
});

const roboto = localFont({
  src: [
    {
      path: '../fonts/Roboto-Medium.ttf',
      weight: '500',
      style: 'normal',
    },
    {
      path: '../fonts/Roboto-Regular.ttf',
      weight: '400',
      style: 'normal',
    },
  ],
  variable: '--font-roboto',
});

function LayoutContent({ children }: { children: React.ReactNode }) {
  const pathname = usePathname();

  // Rotas onde o Navbar NÃO deve aparecer
  const hideNavbarRoutes = [
    '/login',
    '/signup',
    '/cadastro',
    '/cadastro/etapa2',
    '/modals',
    '/inicial',
    '/navbarlogin',
    '/navbarlogin/navbaradmin',
  ];
  const shouldHideNavbar = hideNavbarRoutes.includes(pathname);

  return (
    <>
      {!shouldHideNavbar && <Navbar />}
      {children}
    </>
  );
}

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="pt-BR">
      <body
        className={`${cabourgOT.variable} ${interVariable.variable} ${metropolis.variable} ${roboto.variable}`}
      >
        <ThemeProvider theme={theme}>
          <GlobalStyle />
          <Providers>
            <LayoutContent>{children}</LayoutContent>
          </Providers>
        </ThemeProvider>
      </body>
    </html>
  );
}
