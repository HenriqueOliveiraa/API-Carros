'use client';

import React, { useState } from 'react';
import NavbarLogin from '@/components/navbarlogin/navbarlogin';
import {
  Container,
  HeroSection,
  HeroContent,
  HeroTitle,
  HeroButtons,
  Button,
  ButtonPrimary,
  CarImage,
  ImageOverlay,
  SpecsSection,
  SpecCard,
  SpecValue,
  SpecLabel,
  CarouselSection,
  CarouselContainer,
  CarouselWrapper,
  CarouselTrack,
  CarouselCard,
  CarouselImage,
  CarouselContent,
  CarouselTitle,
  CarouselDescription,
  CarouselButtonLeft,
  CarouselButtonRight,
  CarouselDotsContainer,
  CarouselDot,
} from './styles';

const Inicial = () => {
  const [carouselIndex, setCarouselIndex] = useState(0);

  const carouselSlides = [
    {
      title: 'O poder das possibilidades.',
      description:
        'Permita que o design do novo Mercedes-Benz GLB impressione você. De dentro, de fora e de todos os lados.',
      image:
        'https://images.unsplash.com/photo-1617531653332-bd46c24f2068?w=800&q=80',
    },
    {
      title: 'Exterior.',
      description:
        'Linhas claras e proporções poderosas conferem ao Mercedes-Benz GLB uma aparência marcante.',
      image:
        'https://images.unsplash.com/photo-1618843479313-40f8afb4b4d8?w=800&q=80',
    },
    {
      title: 'Tecnologia Avançada.',
      description:
        'Sistema MBUX intuitivo com assistentes inteligentes para uma experiência de condução premium.',
      image:
        'https://images.unsplash.com/photo-1606664515524-ed2f786a0bd6?w=800&q=80',
    },
    {
      title: 'Conforto Premium.',
      description:
        'Interior espaçoso com acabamento em materiais nobres e assentos ergonômicos para máximo conforto.',
      image:
        'https://images.unsplash.com/photo-1614200187524-dc4b892acf16?w=800&q=80',
    },
  ];

  const handlePrevious = () => {
    setCarouselIndex(prev =>
      prev === 0 ? carouselSlides.length - 2 : prev - 2,
    );
  };

  const handleNext = () => {
    setCarouselIndex(prev =>
      prev >= carouselSlides.length - 2 ? 0 : prev + 2,
    );
  };

  return (
    <>
      <NavbarLogin />
      <Container>
        <HeroSection>
          <HeroContent>
            <HeroTitle>GLB 220 4MATIC</HeroTitle>
            <HeroButtons>
              <Button>Faça uma consulta</Button>
              <ButtonPrimary>Procurar veículos disponíveis</ButtonPrimary>
            </HeroButtons>
          </HeroContent>
          <CarImage
            src="/img/carrofundo.jpg"
            alt="Mercedes-Benz GLB 220 4MATIC"
          />
          <ImageOverlay />
        </HeroSection>

        <SpecsSection>
          <SpecCard>
            <SpecLabel>Até</SpecLabel>
            <SpecValue>
              190<span style={{ fontSize: '1.2rem' }}>cv</span>
            </SpecValue>
            <SpecLabel>Potência combinada</SpecLabel>
          </SpecCard>

          <SpecCard>
            <SpecLabel>Até</SpecLabel>
            <SpecValue>
              565 - 1.800<span style={{ fontSize: '1.2rem' }}>litros</span>
            </SpecValue>
            <SpecLabel>Capacidade do porta-malas</SpecLabel>
          </SpecCard>

          <SpecCard>
            <SpecLabel>Até</SpecLabel>
            <SpecValue>
              7<span style={{ fontSize: '1.2rem' }}>bancos</span>
            </SpecValue>
            <SpecLabel>no compartimento interno do veículo</SpecLabel>
          </SpecCard>

          <SpecCard>
            <SpecLabel>Carga para reboque</SpecLabel>
            <SpecValue>
              750/1.800<span style={{ fontSize: '1.2rem' }}>kg</span>
            </SpecValue>
            <SpecLabel>com freios/sem freios</SpecLabel>
          </SpecCard>
        </SpecsSection>

        <CarouselSection>
          <CarouselContainer>
            <CarouselButtonLeft onClick={handlePrevious}>
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                <path
                  d="M15 18L9 12L15 6"
                  stroke="currentColor"
                  strokeWidth="2"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                />
              </svg>
            </CarouselButtonLeft>

            <CarouselWrapper>
              <CarouselTrack
                style={{ transform: `translateX(-${carouselIndex * 50}%)` }}
              >
                {carouselSlides.map((slide, index) => (
                  <CarouselCard key={index}>
                    <CarouselImage src={slide.image} alt={slide.title} />
                    <CarouselContent>
                      <CarouselTitle>{slide.title}</CarouselTitle>
                      <CarouselDescription>
                        {slide.description}
                      </CarouselDescription>
                    </CarouselContent>
                  </CarouselCard>
                ))}
              </CarouselTrack>
            </CarouselWrapper>

            <CarouselButtonRight onClick={handleNext}>
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                <path
                  d="M9 18L15 12L9 6"
                  stroke="currentColor"
                  strokeWidth="2"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                />
              </svg>
            </CarouselButtonRight>
          </CarouselContainer>

          <CarouselDotsContainer>
            {Array.from({ length: Math.ceil(carouselSlides.length / 2) }).map(
              (_, index) => (
                <CarouselDot
                  key={index}
                  active={carouselIndex / 2 === index}
                  onClick={() => setCarouselIndex(index * 2)}
                />
              ),
            )}
          </CarouselDotsContainer>
        </CarouselSection>
      </Container>
    </>
  );
};

export default Inicial;
