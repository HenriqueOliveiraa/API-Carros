'use client';

import ModalErro from '@/components/modals/errologin/modalerro';

export default function ModalsPage() {
  const handleClose = () => {
    console.log('Modal fechado');
    // Adicione aqui qualquer lógica que você precise quando fechar
  };

  return <ModalErro onClose={handleClose} />;
}
