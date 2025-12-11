'use client';

import React from 'react';
import {
  Overlay,
  Container,
  Header,
  Content,
  ButtonContainer,
  TryAgainButton,
} from './styles';

interface ModalErroProps {
  onClose: () => void;
}

export default function ModalErro({ onClose }: ModalErroProps) {
  const handleTentarNovamente = () => {
    onClose();
  };

  const handleOverlayClick = () => {
    onClose();
  };

  const handleModalClick = (e: React.MouseEvent) => {
    e.stopPropagation();
  };

  return (
    <Overlay onClick={handleOverlayClick}>
      <Container onClick={handleModalClick}>
        <Header>Login inválido!</Header>
        <Content>
          <h2>Verifique seus dados</h2>
          <p>Dados estão incorretos. Verifique os dados e tente novamente</p>
        </Content>
        <ButtonContainer>
          <TryAgainButton onClick={handleTentarNovamente}>
            Tentar novamente
          </TryAgainButton>
        </ButtonContainer>
      </Container>
    </Overlay>
  );
}
