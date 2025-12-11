'use client';

import React, { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import Image from 'next/image';
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
  SubmitButton,
  LoadingContent,
  Spinner,
  SignUpText,
  SignUpLink,
  ErrorMessage,
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

const CadastroEtapa2 = () => {
  const router = useRouter();
  const [telefone, setTelefone] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [errors, setErrors] = useState({
    telefone: '',
    confirmPassword: '',
  });

  useEffect(() => {
    const etapa1Data = sessionStorage.getItem('cadastro_etapa1');
    if (!etapa1Data) {
      router.push('/cadastro');
      return;
    }

    const etapa2Data = sessionStorage.getItem('cadastro_etapa2');
    if (etapa2Data) {
      const dados = JSON.parse(etapa2Data);
      setTelefone(dados.telefone || '');
      setPassword(dados.password || '');
      setConfirmPassword(dados.confirmPassword || '');
    }
  }, [router]);

  const formatTelefone = (value: string) => {
    const numbers = value.replace(/\D/g, '');
    if (numbers.length <= 11) {
      return numbers.replace(/(\d{2})(\d{5})(\d{4})/, '($1) $2-$3');
    }
    return telefone;
  };

  const handleTelefoneChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const formatted = formatTelefone(e.target.value);
    setTelefone(formatted);
    if (errors.telefone) {
      setErrors({ ...errors, telefone: '' });
    }
  };

  const validatePassword = (pass: string, confirmPass: string): string => {
    if (pass.length < 8) {
      return 'A senha deve ter no mínimo 8 caracteres';
    }

    if (!/[A-Z]/.test(pass)) {
      return 'A senha deve conter pelo menos 1 letra maiúscula';
    }

    if (!/[a-z]/.test(pass)) {
      return 'A senha deve conter pelo menos 1 letra minúscula';
    }

    if (!/[!@#$%&*()]/.test(pass)) {
      return 'A senha deve conter pelo menos 1 caractere especial';
    }

    if (pass !== confirmPass) {
      return 'As senhas não coincidem';
    }

    return '';
  };

  const handleSubmit = () => {
    const newErrors = {
      telefone: '',
      confirmPassword: '',
    };

    const telefoneNumbers = telefone.replace(/\D/g, '');
    if (!telefone) {
      newErrors.telefone = 'Por favor, preencha seu telefone';
    } else if (telefoneNumbers.length !== 11) {
      newErrors.telefone = 'Por favor, insira um telefone válido';
    }

    if (!password) {
      newErrors.confirmPassword = 'Por favor, preencha sua senha';
    } else if (!confirmPassword) {
      newErrors.confirmPassword = 'Por favor, confirme sua senha';
    } else {
      const passwordError = validatePassword(password, confirmPassword);
      if (passwordError) {
        newErrors.confirmPassword = passwordError;
      }
    }

    setErrors(newErrors);

    if (Object.values(newErrors).some(error => error !== '')) {
      return;
    }

    setIsLoading(true);
    setTimeout(() => {
      setIsLoading(false);

      const etapa1Data = sessionStorage.getItem('cadastro_etapa1');
      const dados = etapa1Data ? JSON.parse(etapa1Data) : {};

      console.log('Dados completos do cadastro:', {
        ...dados,
        telefone,
        password,
      });

      sessionStorage.removeItem('cadastro_etapa1');
      sessionStorage.removeItem('cadastro_etapa2');

      alert('Cadastro realizado com sucesso!');
      router.push('/login');
    }, 1500);
  };

  const handleBack = () => {
    sessionStorage.setItem(
      'cadastro_etapa2',
      JSON.stringify({
        telefone,
        password,
        confirmPassword,
      }),
    );
    router.push('/cadastro');
  };

  const handleLogin = () => {
    sessionStorage.removeItem('cadastro_etapa1');
    sessionStorage.removeItem('cadastro_etapa2');
    router.push('/login');
  };

  return (
    <Container>
      <LeftSection>
        <LeftImage src="/img/pixels11.png" alt="Background" />
      </LeftSection>

      <RightSection>
        <BackButton onClick={handleBack}>
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
            <Title>Agora preencha os dados de acesso</Title>
            <Subtitle></Subtitle>
          </Header>

          <div>
            <FormGroup>
              <Label>Telefone</Label>
              <InputWrapper>
                <Input
                  type="tel"
                  value={telefone}
                  onChange={handleTelefoneChange}
                  placeholder="Digite seu telefone"
                  maxLength={15}
                  style={{ borderColor: errors.telefone ? '#ef4444' : '' }}
                />
              </InputWrapper>
              {errors.telefone && (
                <ErrorMessage>{errors.telefone}</ErrorMessage>
              )}
            </FormGroup>

            <FormGroup>
              <Label>Senha</Label>
              <InputWrapper>
                <Input
                  type={showPassword ? 'text' : 'password'}
                  value={password}
                  onChange={e => {
                    setPassword(e.target.value);
                    if (errors.confirmPassword) {
                      setErrors({ ...errors, confirmPassword: '' });
                    }
                  }}
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

            <FormGroup>
              <Label>Confirmar Senha</Label>
              <InputWrapper>
                <Input
                  type={showConfirmPassword ? 'text' : 'password'}
                  value={confirmPassword}
                  onChange={e => {
                    setConfirmPassword(e.target.value);
                    if (errors.confirmPassword) {
                      setErrors({ ...errors, confirmPassword: '' });
                    }
                  }}
                  placeholder="Confirme sua senha"
                  style={{
                    borderColor: errors.confirmPassword ? '#ef4444' : '',
                  }}
                />
                <ToggleButton
                  type="button"
                  onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                >
                  {showConfirmPassword ? <EyeOffIcon /> : <EyeIcon />}
                </ToggleButton>
              </InputWrapper>
              {errors.confirmPassword && (
                <ErrorMessage>{errors.confirmPassword}</ErrorMessage>
              )}
            </FormGroup>

            <SubmitButton onClick={handleSubmit} disabled={isLoading}>
              {isLoading ? (
                <LoadingContent>
                  <Spinner />
                  Cadastrando...
                </LoadingContent>
              ) : (
                'Finalizar Cadastro'
              )}
            </SubmitButton>
          </div>

          <SignUpText>
            Já tem uma conta?{' '}
            <SignUpLink onClick={handleLogin}>Faça login</SignUpLink>
          </SignUpText>
        </Card>
      </RightSection>
    </Container>
  );
};

export default CadastroEtapa2;
