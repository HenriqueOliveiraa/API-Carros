'use client';

import React, { useState, useEffect } from 'react';
import Image from 'next/image';
import {
  Container,
  CarouselContainer,
  Slide,
  SlideBackground,
  SlideContent,
  TextContent,
  Subtitle,
  Title,
  Tagline,
  BadgeContainer,
  NavButton,
  DotsContainer,
  Dot,
  ShareButton,
  CardsSection,
  SectionTitle,
  CategoryNav,
  CategoryButton,
  CardsSliderContainer,
  CardsSliderWrapper,
  CardsSlideTrack,
  CardsNavButton,
  CarCard,
  CardImage,
  NewBadge,
  CardContent,
  CarName,
  CarTagline,
  CardActions,
  ActionLink,
  CardsDots,
  CardDot,
  ServiceSection,
  ServiceImage,
  ServiceOverlay,
  ServiceGrid,
  ServiceCard,
  ServiceTitle,
  ServiceDescription,
  ServiceButton,
  ServiceDivider,
} from './styles';

const ChevronLeft = () => (
  <svg
    width="24"
    height="24"
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    strokeWidth="2"
  >
    <polyline points="15 18 9 12 15 6"></polyline>
  </svg>
);

const ChevronRight = () => (
  <svg
    width="24"
    height="24"
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    strokeWidth="2"
  >
    <polyline points="9 18 15 12 9 6"></polyline>
  </svg>
);

const Share2 = () => (
  <svg
    width="20"
    height="20"
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    strokeWidth="2"
  >
    <circle cx="18" cy="5" r="3"></circle>
    <circle cx="6" cy="12" r="3"></circle>
    <circle cx="18" cy="19" r="3"></circle>
    <line x1="8.59" y1="13.51" x2="15.42" y2="17.49"></line>
    <line x1="15.41" y1="6.51" x2="8.59" y2="10.49"></line>
  </svg>
);

const carouselImages = [
  {
    id: 1,
    title: 'NOVO',
    subtitle: 'Civic',
    tagline: '2026',
    subtitleBottom: 'ADVANCED',
    taglineBottom: 'HYBRID',
    image:
      'https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEgCN7A9Yh-2oZ_-tkV79JGFyaEsanmq34zfNK9upYQ7Ma6Db8yQOG4WkUdCa_912_ekSTB_4VQymx2Vt0jZMZfaqgo59c1cypsQD4OwPFcnZazsHdyVE0eReYDZjZ3CdvlM8bmWe6Vi77mS/s2048/Honda-Civic-2021+%25285%2529.jpeg',
    badge: false,
  },
  {
    id: 2,
    title: 'NOVO',
    subtitle: 'CR-V',
    tagline: '2025',
    subtitleBottom: 'ADVANCED',
    taglineBottom: 'HYBRID',
    image:
      'https://www.honda.com.br/automoveis/sites/hab/files/2025-10/crv.webp',
    badge: false,
  },
  {
    id: 3,
    title: 'NOVO',
    subtitle: 'Corolla',
    tagline: '2025',
    subtitleBottom: 'ELEGÂNCIA',
    taglineBottom: 'E TECNOLOGIA',
    image:
      'https://upload.wikimedia.org/wikipedia/commons/f/f6/Toyota_Corolla_Limousine_Hybrid_Genf_2019_1Y7A5576.jpg',
    badge: false,
  },
  {
    id: 4,
    title: 'NOVO',
    subtitle: 'RAV4',
    tagline: '2025',
    subtitleBottom: 'CONECTADO',
    taglineBottom: 'AO FUTURO',
    image:
      'https://www.toyotacomunica.com.br/wp-content/uploads/2024/04/Destaque-RAV4-PHEV-copiar.jpg',
    badge: false,
  },
  {
    id: 5,
    title: 'NOVO',
    subtitle: 'A3 Sedan',
    tagline: '2025',
    subtitleBottom: 'SOFISTICAÇÃO',
    taglineBottom: 'PREMIUM',
    image:
      'https://production.autoforce.com/uploads/version/profile_image/10036/comprar-performance-black-40-tfsi-s-tronic_7738837bc4.png',
    badge: false,
  },
  {
    id: 6,
    title: 'NOVO',
    subtitle: 'Q5',
    tagline: '2026',
    subtitleBottom: 'ESPAÇO',
    taglineBottom: 'E CONFORTO',
    image:
      'https://image.webmotors.com.br/_fotos/anunciousados/gigante/2025/202511/20251129/audi-q5-2.0-55-tfsie-phev-performance-quattro-s-tronic-wmimagem00595393182.jpg',
    badge: false,
  },
  {
    id: 7,
    title: 'NOVO',
    subtitle: 'Série 3',
    tagline: '2025',
    subtitleBottom: 'DESEMPENHO',
    taglineBottom: 'ESPORTIVO',
    image:
      'https://cdn.motor1.com/images/mgl/jlwrMo/s1/novo-bmw-serie-3-2027---projecao.jpg',
    badge: false,
  },
  {
    id: 8,
    title: 'NOVO',
    subtitle: 'X3',
    tagline: '2025',
    subtitleBottom: 'VERSATILIDADE',
    taglineBottom: 'PREMIUM',
    image:
      'https://imgd.aeplcdn.com/664x374/n/cw/ec/179903/x3-exterior-left-front-three-quarter.jpeg?isig=0&q=80',
    badge: false,
  },
  {
    id: 9,
    title: 'NOVO',
    subtitle: 'Classe C',
    tagline: '2025',
    subtitleBottom: 'LUXO',
    taglineBottom: 'REFINADO',
    image:
      'https://cdn.motor1.com/images/mgl/qnAOz/s3/mercedes-c-klasse-limousine-2021.jpg',
    badge: false,
  },
  {
    id: 10,
    title: 'NOVO',
    subtitle: 'GLC',
    tagline: '2025',
    subtitleBottom: 'TECNOLOGIA',
    taglineBottom: 'AVANÇADA',
    image:
      'https://cdn.motor1.com/images/mgl/JOXpRA/s1/2023-mercedes-glc-coupe.jpg',
    badge: false,
  },
];

const carCards = [
  {
    id: 1,
    name: 'Civic',
    tagline: 'Híbrido Avançado',
    image:
      'https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEgCN7A9Yh-2oZ_-tkV79JGFyaEsanmq34zfNK9upYQ7Ma6Db8yQOG4WkUdCa_912_ekSTB_4VQymx2Vt0jZMZfaqgo59c1cypsQD4OwPFcnZazsHdyVE0eReYDZjZ3CdvlM8bmWe6Vi77mS/s2048/Honda-Civic-2021+%25285%2529.jpeg',
    isNew: true,
    type: 'sedan',
  },
  {
    id: 2,
    name: 'CR-V',
    tagline: 'SUV Tecnológico',
    image:
      'https://www.honda.com.br/automoveis/sites/hab/files/2025-10/crv.webp',
    isNew: false,
    type: 'suv',
  },
  {
    id: 3,
    name: 'Accord',
    tagline: 'Elegância Refinada',
    image:
      'https://cdn.motor1.com/images/mgl/g4gO77/s1/honda-accord-advanced-hybrid-2025-br.webp',
    isNew: false,
    type: 'sedan',
  },
  {
    id: 4,
    name: 'HR-V',
    tagline: 'Design Moderno',
    image:
      'https://www.hondacaiuas.com.br/wp-content/uploads/2025/04/honda-hr-v-2025-scaled.webp',
    isNew: true,
    type: 'suv',
  },
  {
    id: 5,
    name: 'Fit',
    tagline: 'Compacto Versátil',
    image:
      'https://www.webmotors.com.br/imagens/prod/347394/HONDA_FIT_1.5_PERSONAL_16V_FLEX_4P_AUTOMATICO_34739416402009260.webp?s=fill&w=170&h=125&t=true',
    isNew: false,
    type: 'hatchback',
  },
  {
    id: 6,
    name: 'Pilot',
    tagline: 'Espaço Familiar',
    image:
      'https://hips.hearstapps.com/hmg-prod/images/2025-honda-pilot-black-edition-01-65e1e8b47b986.jpg?crop=0.587xw:0.587xh;0.231xw,0.262xh&resize=2048:*',
    isNew: false,
    type: 'suv',
  },

  {
    id: 7,
    name: 'Corolla',
    tagline: 'Sedan Premium',
    image:
      'https://upload.wikimedia.org/wikipedia/commons/f/f6/Toyota_Corolla_Limousine_Hybrid_Genf_2019_1Y7A5576.jpg',
    isNew: true,
    type: 'sedan',
  },
  {
    id: 8,
    name: 'RAV4',
    tagline: 'Híbrido Potente',
    image:
      'https://www.toyotacomunica.com.br/wp-content/uploads/2024/04/Destaque-RAV4-PHEV-copiar.jpg',
    isNew: true,
    type: 'suv',
  },
  {
    id: 9,
    name: 'Camry',
    tagline: 'Conforto Total',
    image:
      'https://s2-autoesporte.glbimg.com/JR5DhkIElu7jRsAAvhvr2E7y_Ro=/0x0:620x413/600x0/smart/filters:gifv():strip_icc()/i.s3.glbimg.com/v1/AUTH_cf9d035bf26b4646b105bd958f32089d/internal_photos/bs/2020/6/z/uiNmhfTEqUPBqL6xszhA/2018-06-14-camryfrtente.jpg',
    isNew: false,
    type: 'sedan',
  },
  {
    id: 10,
    name: 'Hilux',
    tagline: 'Pickup Robusta',
    image:
      'https://cdn.motor1.com/images/mgl/Rq6Oqm/s3/toyota-hilux-2024-mild-hybrid-48-v---esterni-in-studio.jpg',
    isNew: true,
    type: 'suv',
  },
  {
    id: 11,
    name: 'Yaris',
    tagline: 'Urbano Econômico',
    image:
      'https://blog.toyotasulpar.com.br/wp-content/uploads/2022/07/yaris-2023-seda-hatch-toyota-sulpar-curitiba.jpg',
    isNew: false,
    type: 'hatchback',
  },
  {
    id: 12,
    name: 'Highlander',
    tagline: 'SUV Premium',
    image:
      'https://www.automaistv.com.br/wp-content/uploads/2023/02/2024_Toyota_GrandHighlander_StormCloud_001_edited.jpg',
    isNew: false,
    type: 'suv',
  },

  {
    id: 13,
    name: 'A3 Sedan',
    tagline: 'Sofisticação Premium',
    image:
      'https://production.autoforce.com/uploads/version/profile_image/10036/comprar-performance-black-40-tfsi-s-tronic_7738837bc4.png',
    isNew: true,
    type: 'sedan',
  },
  {
    id: 14,
    name: 'Q5',
    tagline: 'SUV Luxuoso',
    image:
      'https://image.webmotors.com.br/_fotos/anunciousados/gigante/2025/202511/20251129/audi-q5-2.0-55-tfsie-phev-performance-quattro-s-tronic-wmimagem00595393182.jpg',
    isNew: true,
    type: 'suv',
  },
  {
    id: 15,
    name: 'A4',
    tagline: 'Elegância Executiva',
    image:
      'https://upload.wikimedia.org/wikipedia/commons/3/35/Audi_A4_B9_sedans_%28FL%29_1X7A2441.jpg',
    isNew: false,
    type: 'sedan',
  },
  {
    id: 16,
    name: 'Q3',
    tagline: 'Compacto Premium',
    image:
      'https://www.comprecar.com.br/storage/news/featured/ef3613f1-2590-4a74-aecd-d291f5defe27.jpeg',
    isNew: false,
    type: 'suv',
  },
  {
    id: 17,
    name: 'A3 Sportback',
    tagline: 'Design Esportivo',
    image:
      'https://autoentusiastas.com.br/ae/wp-content/uploads/2025/06/Audi-A3_Sportback-2025-02.jpg',
    isNew: true,
    type: 'hatchback',
  },
  {
    id: 18,
    name: 'Q7',
    tagline: 'Grande Porte',
    image: 'https://cdn.motor1.com/images/mgl/ojxeBq/s1/2025-audi-q7.jpg',
    isNew: false,
    type: 'suv',
  },

  {
    id: 19,
    name: 'Série 3',
    tagline: 'Esportivo Premium',
    image:
      'https://cdn.motor1.com/images/mgl/jlwrMo/s1/novo-bmw-serie-3-2027---projecao.jpg',
    isNew: true,
    type: 'sedan',
  },
  {
    id: 20,
    name: 'X3',
    tagline: 'SUV Dinâmico',
    image:
      'https://imgd.aeplcdn.com/664x374/n/cw/ec/179903/x3-exterior-left-front-three-quarter.jpeg?isig=0&q=80',
    isNew: true,
    type: 'suv',
  },
  {
    id: 21,
    name: 'Série 5',
    tagline: 'Luxo Executivo',
    image: 'https://cdn.motor1.com/images/mgl/1Z4Plp/s1/bmw-530e-2023.webp',
    isNew: false,
    type: 'sedan',
  },
  {
    id: 22,
    name: 'X5',
    tagline: 'Performance Superior',
    image:
      'https://cdn.motor1.com/images/mgl/7kzJP/s3/2020-bmw-x5-m-competition.jpg',
    isNew: false,
    type: 'suv',
  },
  {
    id: 23,
    name: 'Série 1',
    tagline: 'Compacto Esportivo',
    image:
      'https://www.topgear.com/sites/default/files/2024/11/P90577405_highRes_the-bmw-120.jpg',
    isNew: true,
    type: 'hatchback',
  },
  {
    id: 24,
    name: 'X1',
    tagline: 'SUV Urbano',
    image:
      'https://grandbrasil.com.br/wp-content/uploads/2019/11/7_BMW_X1_Rockset.webp',
    isNew: false,
    type: 'suv',
  },

  {
    id: 25,
    name: 'Classe C',
    tagline: 'Refinamento Alemão',
    image:
      'https://cdn.motor1.com/images/mgl/qnAOz/s3/mercedes-c-klasse-limousine-2021.jpg',
    isNew: true,
    type: 'sedan',
  },
  {
    id: 26,
    name: 'GLC',
    tagline: 'SUV Elegante',
    image:
      'https://cdn.motor1.com/images/mgl/JOXpRA/s1/2023-mercedes-glc-coupe.jpg',
    isNew: true,
    type: 'suv',
  },
  {
    id: 27,
    name: 'Classe E',
    tagline: 'Tecnologia Avançada',
    image:
      'https://cdn.motor1.com/images/mgl/E63b3z/s1/mercedes-benz-classe-e-e300-2025.webp',
    isNew: false,
    type: 'sedan',
  },
  {
    id: 28,
    name: 'GLE',
    tagline: 'Porte Imponente',
    image:
      'https://vehicle-images.dealerinspire.com/stock-images/thumbnails/large/chrome/fa8a965f4d1be02ca1ca1b06a8a44495.png',
    isNew: false,
    type: 'suv',
  },
  {
    id: 29,
    name: 'Classe A',
    tagline: 'Compacto Luxuoso',
    image:
      'https://s2-autoesporte.glbimg.com/j8ZFtpgvTvWzOSU9UE7v4Ht9SSI=/0x0:1400x911/924x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_cf9d035bf26b4646b105bd958f32089d/internal_photos/bs/2022/s/0/1P0s6BQDCxflOYYw7zSA/classea01.jpg',
    isNew: true,
    type: 'hatchback',
  },
  {
    id: 30,
    name: 'GLA',
    tagline: 'Aventura Urbana',
    image:
      'https://cdn.motor1.com/images/mgl/z2xpE/s1/4x3/mercedes-benz-gla-35-amg-2020.webp',
    isNew: false,
    type: 'suv',
  },
];

const HomePage = () => {
  const [currentSlide, setCurrentSlide] = useState(0);
  const [isHovered, setIsHovered] = useState(false);
  const [cardsSlide, setCardsSlide] = useState(0);
  const [isCardsHovered, setIsCardsHovered] = useState(false);
  const [activeCategory, setActiveCategory] = useState('todos');

  const filteredCars =
    activeCategory === 'todos'
      ? carCards
      : carCards.filter(car => car.type === activeCategory);

  const cardsPerView = 4;
  const totalCardsSlides = Math.ceil(filteredCars.length / cardsPerView);

  useEffect(() => {
    setCardsSlide(0);
  }, [activeCategory]);

  useEffect(() => {
    if (!isHovered) {
      const interval = setInterval(() => {
        setCurrentSlide(prev => (prev + 1) % carouselImages.length);
      }, 5000);

      return () => clearInterval(interval);
    }
  }, [isHovered]);

  useEffect(() => {
    if (!isCardsHovered) {
      const interval = setInterval(() => {
        setCardsSlide(prev => (prev + 1) % totalCardsSlides);
      }, 4000);

      return () => clearInterval(interval);
    }
  }, [isCardsHovered, totalCardsSlides]);

  const nextSlide = () => {
    setCurrentSlide(prev => (prev + 1) % carouselImages.length);
  };

  const prevSlide = () => {
    setCurrentSlide(
      prev => (prev - 1 + carouselImages.length) % carouselImages.length,
    );
  };

  const goToSlide = (index: number) => {
    setCurrentSlide(index);
  };

  const nextCardsSlide = () => {
    setCardsSlide(prev => (prev + 1) % totalCardsSlides);
  };

  const prevCardsSlide = () => {
    setCardsSlide(prev => (prev - 1 + totalCardsSlides) % totalCardsSlides);
  };

  const goToCardsSlide = (index: number) => {
    setCardsSlide(index);
  };

  const handleCategoryChange = (category: string) => {
    setActiveCategory(category);
  };

  return (
    <Container>
      <CarouselContainer
        onMouseEnter={() => setIsHovered(true)}
        onMouseLeave={() => setIsHovered(false)}
      >
        {carouselImages.map((slide, index) => (
          <Slide key={slide.id} $isActive={index === currentSlide}>
            <SlideBackground $bgImage={slide.image}>
              <SlideContent>
                <TextContent>
                  <Subtitle>{slide.title}</Subtitle>
                  <Title>{slide.subtitle}</Title>
                  <Tagline>{slide.tagline}</Tagline>
                  <Subtitle style={{ marginTop: '2rem' }}>
                    {slide.subtitleBottom}
                  </Subtitle>
                  <Tagline>{slide.taglineBottom}</Tagline>
                </TextContent>
              </SlideContent>

              {slide.badge && (
                <BadgeContainer>
                  <svg width="80" height="80" viewBox="0 0 80 80">
                    <circle
                      cx="40"
                      cy="40"
                      r="38"
                      fill="white"
                      stroke="#e5e7eb"
                      strokeWidth="2"
                    />
                    <text
                      x="40"
                      y="35"
                      fontSize="10"
                      fontWeight="bold"
                      fill="#000"
                      textAnchor="middle"
                    >
                      6 ANOS
                    </text>
                    <text
                      x="40"
                      y="48"
                      fontSize="8"
                      fontWeight="bold"
                      fill="#000"
                      textAnchor="middle"
                    >
                      GARANTIA
                    </text>
                    <text
                      x="40"
                      y="58"
                      fontSize="7"
                      fill="#666"
                      textAnchor="middle"
                    >
                      TOTAL
                    </text>
                  </svg>
                </BadgeContainer>
              )}
            </SlideBackground>
          </Slide>
        ))}

        {isHovered && (
          <>
            <NavButton $position="left" onClick={prevSlide}>
              <ChevronLeft />
            </NavButton>
            <NavButton $position="right" onClick={nextSlide}>
              <ChevronRight />
            </NavButton>
          </>
        )}

        <DotsContainer>
          {carouselImages.map((_, index) => (
            <Dot
              key={index}
              $isActive={index === currentSlide}
              onClick={() => goToSlide(index)}
            />
          ))}
        </DotsContainer>

        <ShareButton>
          <Share2 />
        </ShareButton>
      </CarouselContainer>

      <CardsSection>
        <SectionTitle>Descubra o Automóvel ideal para você</SectionTitle>

        <CategoryNav>
          <CategoryButton
            $isActive={activeCategory === 'todos'}
            onClick={() => handleCategoryChange('todos')}
          >
            Todos
          </CategoryButton>
          <CategoryButton
            $isActive={activeCategory === 'hatchback'}
            onClick={() => handleCategoryChange('hatchback')}
          >
            Hatchback
          </CategoryButton>
          <CategoryButton
            $isActive={activeCategory === 'sedan'}
            onClick={() => handleCategoryChange('sedan')}
          >
            Sedan
          </CategoryButton>
          <CategoryButton
            $isActive={activeCategory === 'suv'}
            onClick={() => handleCategoryChange('suv')}
          >
            SUV
          </CategoryButton>
        </CategoryNav>

        <CardsSliderContainer
          onMouseEnter={() => setIsCardsHovered(true)}
          onMouseLeave={() => setIsCardsHovered(false)}
        >
          <CardsSliderWrapper>
            <CardsSlideTrack
              $translateX={cardsSlide}
              $cardsPerView={cardsPerView}
            >
              {filteredCars.map(car => (
                <CarCard key={car.id}>
                  <CardImage>
                    {car.isNew && <NewBadge>Novo!</NewBadge>}
                    <Image
                      src={car.image}
                      alt={car.name}
                      width={400}
                      height={256}
                      unoptimized
                      style={{
                        width: '100%',
                        height: '16rem',
                        objectFit: 'cover',
                      }}
                    />
                  </CardImage>

                  <CardContent>
                    <CarName>{car.name}</CarName>
                    <CarTagline>{car.tagline}</CarTagline>

                    <CardActions>
                      <ActionLink>
                        Explore
                        <ChevronRight />
                      </ActionLink>
                      <ActionLink>
                        Tenho interesse
                        <ChevronRight />
                      </ActionLink>
                    </CardActions>
                  </CardContent>
                </CarCard>
              ))}
            </CardsSlideTrack>
          </CardsSliderWrapper>

          {isCardsHovered && totalCardsSlides > 1 && (
            <>
              <CardsNavButton $position="left" onClick={prevCardsSlide}>
                <ChevronLeft />
              </CardsNavButton>
              <CardsNavButton $position="right" onClick={nextCardsSlide}>
                <ChevronRight />
              </CardsNavButton>
            </>
          )}
        </CardsSliderContainer>

        <CardsDots>
          {[...Array(totalCardsSlides)].map((_, i) => (
            <CardDot
              key={i}
              $isActive={i === cardsSlide}
              onClick={() => goToCardsSlide(i)}
            />
          ))}
        </CardsDots>
      </CardsSection>

      <ServiceSection>
        <ServiceImage
          src="https://http2.mlstatic.com/D_NQ_747468-MLA86730440436_072025-OO.jpg"
          alt="Honda Grade"
        />
        <ServiceOverlay>
          <ServiceGrid>
            <ServiceCard>
              <ServiceTitle>Atendimento exclusivo</ServiceTitle>
              <ServiceDescription>
                Conheça nossos canais de atendimento especializados.
              </ServiceDescription>
              <ServiceButton>Fale Conosco</ServiceButton>
            </ServiceCard>

            <ServiceDivider />

            <ServiceCard>
              <ServiceTitle>Encontre uma concessionária</ServiceTitle>
              <ServiceDescription>
                Existe uma concessionária perto de você, faça uma visita.
              </ServiceDescription>
              <ServiceButton>Encontrar</ServiceButton>
            </ServiceCard>

            <ServiceDivider />

            <ServiceCard>
              <ServiceTitle>Test Drive</ServiceTitle>
              <ServiceDescription>
                Confira a experiência Honda. Agende um test drive.
              </ServiceDescription>
              <ServiceButton>Agendar</ServiceButton>
            </ServiceCard>
          </ServiceGrid>
        </ServiceOverlay>
      </ServiceSection>
    </Container>
  );
};

export default HomePage;
