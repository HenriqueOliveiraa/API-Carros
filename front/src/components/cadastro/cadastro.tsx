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
  SubmitButton,
  LoadingContent,
  Spinner,
  SignUpText,
  SignUpLink,
  ErrorMessage,
} from './styles';

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

const Cadastro = () => {
  const router = useRouter();
  const [nome, setNome] = useState('');
  const [email, setEmail] = useState('');
  const [cpf, setCpf] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [errors, setErrors] = useState({
    nome: '',
    email: '',
    cpf: '',
  });

  useEffect(() => {
    const etapa1Data = sessionStorage.getItem('cadastro_etapa1');
    if (etapa1Data) {
      const dados = JSON.parse(etapa1Data);
      setNome(dados.nome || '');
      setEmail(dados.email || '');
      setCpf(dados.cpf || '');
    }
  }, []);

  const formatCpf = (value: string) => {
    const numbers = value.replace(/\D/g, '');
    if (numbers.length <= 11) {
      return numbers
        .replace(/(\d{3})(\d)/, '$1.$2')
        .replace(/(\d{3})(\d)/, '$1.$2')
        .replace(/(\d{3})(\d{1,2})/, '$1-$2')
        .replace(/(-\d{2})\d+?$/, '$1');
    }
    return cpf;
  };

  const validateCpf = (cpf: string) => {
    const numbers = cpf.replace(/\D/g, '');

    if (numbers.length !== 11) return false;

    if (/^(\d)\1{10}$/.test(numbers)) return false;

    let sum = 0;
    let remainder;

    for (let i = 1; i <= 9; i++) {
      sum += parseInt(numbers.substring(i - 1, i)) * (11 - i);
    }

    remainder = (sum * 10) % 11;
    if (remainder === 10 || remainder === 11) remainder = 0;
    if (remainder !== parseInt(numbers.substring(9, 10))) return false;

    sum = 0;
    for (let i = 1; i <= 10; i++) {
      sum += parseInt(numbers.substring(i - 1, i)) * (12 - i);
    }

    remainder = (sum * 10) % 11;
    if (remainder === 10 || remainder === 11) remainder = 0;
    if (remainder !== parseInt(numbers.substring(10, 11))) return false;

    return true;
  };

  const handleCpfChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const formatted = formatCpf(e.target.value);
    setCpf(formatted);
    if (errors.cpf) {
      setErrors({ ...errors, cpf: '' });
    }
  };

  const handleSubmit = () => {
    const newErrors = {
      nome: '',
      email: '',
      cpf: '',
    };

    if (!nome) {
      newErrors.nome = 'Por favor, preencha seu nome completo';
    }

    if (!email) {
      newErrors.email = 'Por favor, preencha seu e-mail';
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
      newErrors.email = 'Por favor, insira um e-mail válido';
    }

    if (!cpf) {
      newErrors.cpf = 'Por favor, preencha seu CPF';
    } else if (!validateCpf(cpf)) {
      newErrors.cpf = 'Por favor, insira um CPF válido';
    }

    setErrors(newErrors);

    if (Object.values(newErrors).some(error => error !== '')) {
      return;
    }

    setIsLoading(true);
    setTimeout(() => {
      setIsLoading(false);
      sessionStorage.setItem(
        'cadastro_etapa1',
        JSON.stringify({ nome, email, cpf }),
      );
      router.push('/cadastro/etapa2');
    }, 800);
  };

  const handleLogin = () => {
    router.push('/login');
  };

  const handleBackToLogin = () => {
    sessionStorage.removeItem('cadastro_etapa1');
    router.push('/login');
  };

  return (
    <Container>
      <LeftSection>
        <LeftImage src="/img/pixels11.png" alt="Background" />
      </LeftSection>

      <RightSection>
        <BackButton onClick={handleBackToLogin}>
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
            <Title>Preencha os dados abaixo para se cadastrar</Title>
            <Subtitle></Subtitle>
          </Header>

          <div>
            <FormGroup>
              <Label>Nome Completo</Label>
              <InputWrapper>
                <Input
                  type="text"
                  value={nome}
                  onChange={e => {
                    setNome(e.target.value);
                    if (errors.nome) {
                      setErrors({ ...errors, nome: '' });
                    }
                  }}
                  placeholder="Digite seu nome completo"
                  style={{ borderColor: errors.nome ? '#ef4444' : '' }}
                />
              </InputWrapper>
              {errors.nome && <ErrorMessage>{errors.nome}</ErrorMessage>}
            </FormGroup>

            <FormGroup>
              <Label>Email</Label>
              <InputWrapper>
                <Input
                  type="email"
                  value={email}
                  onChange={e => {
                    setEmail(e.target.value);
                    if (errors.email) {
                      setErrors({ ...errors, email: '' });
                    }
                  }}
                  placeholder="Digite seu e-mail"
                  style={{ borderColor: errors.email ? '#ef4444' : '' }}
                />
              </InputWrapper>
              {errors.email && <ErrorMessage>{errors.email}</ErrorMessage>}
            </FormGroup>

            <FormGroup>
              <Label>CPF</Label>
              <InputWrapper>
                <Input
                  type="text"
                  value={cpf}
                  onChange={handleCpfChange}
                  placeholder="Digite seu CPF"
                  maxLength={14}
                  style={{ borderColor: errors.cpf ? '#ef4444' : '' }}
                />
              </InputWrapper>
              {errors.cpf && <ErrorMessage>{errors.cpf}</ErrorMessage>}
            </FormGroup>

            <SubmitButton onClick={handleSubmit} disabled={isLoading}>
              {isLoading ? (
                <LoadingContent>
                  <Spinner />
                  Avançando...
                </LoadingContent>
              ) : (
                'Continuar'
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

export default Cadastro;
