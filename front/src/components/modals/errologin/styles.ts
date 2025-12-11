import styled from 'styled-components';

export const Overlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.67);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
`;

export const Container = styled.div`
  width: 450px;
  height: 260px;
  background-color: #fff;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
`;

export const Header = styled.div`
  background-color: #036800ff;
  color: #fff;
  padding: 24px 15px;
  font-size: 28px;
  font-weight: 600;
  font-family: var(--font-cabourg);
`;

export const Content = styled.div`
  padding: 15px;
  background-color: #f5f5f5;

  h2 {
    font-size: 20px;
    font-weight: 600;
    color: #333;
    margin: 0 0 16px 0;
  }

  p {
    font-size: 16px;
    color: #666;
    margin: 0;
    line-height: 1.5;
  }
`;

export const ButtonContainer = styled.div`
  display: flex;
  border-top: 1px solid #ddd;
  margin-top: 0rem;
`;

export const TryAgainButton = styled.button`
  width: 100%;
  padding: 20px;
  font-size: 18px;
  font-weight: 600;
  color: #036800ff;
  background-color: #fff;
  border: none;
  cursor: pointer;
  transition: background-color 0.2s;

  &:hover {
  }

  &:active {
    background-color: #f0f0f0;
  }
`;
