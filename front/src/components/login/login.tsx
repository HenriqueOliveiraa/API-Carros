'use client';

import React, { useState } from 'react';
import { useRouter } from 'next/navigation';
import Image from 'next/image';
import ModalErro from '@/components/modals/errologin/modalerro';
import {
  Container,
  LeftSection,
  LeftImage,
  RightSection,
  BackButton,
  Card,
  Header,
  LogoBox,
  Title,
  Subtitle,
  FormGroup,
  Label,
  InputWrapper,
  Input,
  ToggleButton,
  RememberRow,
  CheckboxLabel,
  ForgotButton,
  SubmitButton,
  LoadingContent,
  Spinner,
  SignUpText,
  SignUpLink,
} from './styles';

const EyeIcon = () => (
  <svg
    width="20"
    height="20"
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    strokeWidth="2"
  >
    <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path>
    <circle cx="12" cy="12" r="3"></circle>
  </svg>
);

const EyeOffIcon = () => (
  <svg
    width="20"
    height="20"
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    strokeWidth="2"
  >
    <path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"></path>
    <line x1="1" y1="1" x2="23" y2="23"></line>
  </svg>
);

const ArrowLeft = () => (
  <svg
    width="24"
    height="24"
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    strokeWidth="2"
  >
    <polyline points="12 19 5 12 12 5"></polyline>
  </svg>
);

const Login = () => {
  const router = useRouter();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [showModalErro, setShowModalErro] = useState(false);

  const handleSubmit = () => {
    setIsLoading(true);
    setTimeout(() => {
      setIsLoading(false);

      // Validação de credenciais
      if (email === 'user@gmail.com' && password === '123') {
        alert('Login realizado com sucesso!');
        // router.push('/dashboard');
      } else {
        // Mostra o modal de erro
        setShowModalErro(true);
      }
    }, 1500);
  };

  const handleCloseModal = () => {
    setShowModalErro(false);
  };

  const handleForgotPassword = () => {
    alert('Recuperação de senha');
  };

  const handleSignUp = () => {
    router.push('/cadastro');
  };

  const handleBackToHome = () => {
    router.push('/');
  };

  return (
    <>
      <Container>
        <LeftSection>
          <LeftImage src="/img/pixels11.png" alt="Background" />
        </LeftSection>

        <RightSection>
          <BackButton onClick={handleBackToHome}>
            <ArrowLeft />
          </BackButton>

          <Card>
            <Header>
              <LogoBox>
                <Image
                  src="/img/logocarcopy.png"
                  alt="Logo"
                  width={250}
                  height={250}
                  priority
                />
              </LogoBox>
              <Title>Entre com suas credenciais</Title>
              <Subtitle></Subtitle>
            </Header>

            <div>
              <FormGroup>
                <Label>Email</Label>
                <InputWrapper>
                  <Input
                    type="email"
                    value={email}
                    onChange={e => setEmail(e.target.value)}
                    placeholder="Digite seu e-mail "
                  />
                </InputWrapper>
              </FormGroup>

              <FormGroup>
                <Label>Senha</Label>
                <InputWrapper>
                  <Input
                    type={showPassword ? 'text' : 'password'}
                    value={password}
                    onChange={e => setPassword(e.target.value)}
                    placeholder="Digite sua senha"
                  />
                  <ToggleButton
                    type="button"
                    onClick={() => setShowPassword(!showPassword)}
                  >
                    {showPassword ? <EyeOffIcon /> : <EyeIcon />}
                  </ToggleButton>
                </InputWrapper>
              </FormGroup>

              <RememberRow>
                <CheckboxLabel>
                  <input type="checkbox" />
                  <span>Lembrar-me</span>
                </CheckboxLabel>
                <ForgotButton onClick={handleForgotPassword}>
                  Esqueci a senha
                </ForgotButton>
              </RememberRow>

              <SubmitButton onClick={handleSubmit} disabled={isLoading}>
                {isLoading ? (
                  <LoadingContent>
                    <Spinner />
                    Entrando...
                  </LoadingContent>
                ) : (
                  'Entrar'
                )}
              </SubmitButton>
            </div>

            <SignUpText>
              Não tem uma conta?{' '}
              <SignUpLink onClick={handleSignUp}>Cadastre-se</SignUpLink>
            </SignUpText>
          </Card>
        </RightSection>
      </Container>

      {showModalErro && <ModalErro onClose={handleCloseModal} />}
    </>
  );
};

export default Login;
