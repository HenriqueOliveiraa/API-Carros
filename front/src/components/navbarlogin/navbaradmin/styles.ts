import styled from 'styled-components';

export const Container = styled.div`
  display: flex;
  min-height: 100vh;
  background-color: #1a1a1a;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
`;

export const Sidebar = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 260px;
  height: 100vh;
  background: #036800ff;
  padding: 30px 0px;
  display: flex;
  flex-direction: column;
  overflow-y: auto;
  z-index: 1000;
`;

export const Logo = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 10px;
  padding: 0 20px;
`;

export const LogoImage = styled.img`
  width: 200px;
  height: 200px;
  object-fit: contain;
  margin: -50px 0 -70px 0;
  margin-left: -10px;
`;

export const LogoText = styled.h1`
  color: #ddddddff;
  font-family: var(--font-cabourg);
  font-size: 24px;
  font-weight: 600;
  margin-top: 0px;
  margin-bottom: 1rem;
  letter-spacing: 0.5px;
`;

export const MenuItem = styled.div<{ active?: boolean }>`
  display: flex;
  align-items: center;
  padding: 14px 20px;
  margin: 6px 0;
  cursor: pointer;
  border-radius: 0;
  transition: all 0.3s ease;
  background-color: ${props =>
    props.active ? 'rgba(255, 255, 255, 0.25)' : 'transparent'};
  border-left: 3px solid transparent;
  position: relative;

  &:hover {
    border-left-color: #ffffff;
    background-color: rgba(128, 128, 128, 0.3);
  }
`;

export const MenuIcon = styled.div`
  color: rgba(255, 255, 255, 0.8);
  font-size: 18px;
  margin-right: 14px;
  display: flex;
  align-items: center;
  transition: color 0.3s ease;

  ${MenuItem}:hover & {
    color: #ffffff;
  }
`;

export const MenuText = styled.span`
  color: rgba(255, 255, 255, 0.61);
  font-size: 14px;
  font-family: var(--font-metropolis);
  font-weight: 700;
  letter-spacing: 0.3px;
  transition: color 0.3s ease;

  ${MenuItem}:hover & {
    color: #ffffff;
  }
`;

export const Divider = styled.div`
  height: 1px;
  background-color: rgba(255, 255, 255, 1);
  margin: 15px 20px;
`;

export const Spacer = styled.div`
  flex: 1;
`;

export const LogoutButton = styled(MenuItem)`
  background-color: transparent;
`;

export const Content = styled.div`
  flex: 1;
  margin-left: 260px;
  background-color: white;
`;

export const ContentHeader = styled.div``;

export const ContentTitle = styled.h2``;

export const PlanList = styled.div``;

export const PlanItem = styled.div``;
