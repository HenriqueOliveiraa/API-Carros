'use client';

import React, { useState, useMemo, useRef, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import Image from 'next/image';
import {
  Header,
  HeaderContent,
  HeaderLeft,
  ModelsButton,
  Logo,
  HeaderRight,
  LocationInfo,
  FindDealer,
  InterestButton,
  MenuOverlay,
  MenuPanel,
  MenuContent,
  TabsContainer,
  Tab,
  FiltersContainer,
  FilterDropdownWrapper,
  FilterDropdown,
  DropdownList,
  DropdownOption,
  CarsGrid,
  CarCard,
  CarImageWrapper,
  CarImage,
  NewBadge,
  CarInfo,
  CarName,
  ArrowIcon,
  PaginationContainer,
  PaginationInfo,
  PaginationButtons,
  PageButton,
} from './styles';

const MapPin = () => (
  <svg
    width="18"
    height="18"
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    strokeWidth="2"
  >
    <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"></path>
    <circle cx="12" cy="10" r="3"></circle>
  </svg>
);

const ChevronDown = () => (
  <svg
    width="20"
    height="20"
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    strokeWidth="2"
  >
    <polyline points="6 9 12 15 18 9"></polyline>
  </svg>
);

const ArrowRight = () => (
  <svg
    width="20"
    height="20"
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    strokeWidth="2"
  >
    <line x1="5" y1="12" x2="19" y2="12"></line>
    <polyline points="12 5 19 12 12 19"></polyline>
  </svg>
);

const EditIcon = () => (
  <svg
    width="14"
    height="14"
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    strokeWidth="2"
  >
    <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path>
    <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path>
  </svg>
);

const ChevronLeft = () => (
  <svg
    width="20"
    height="20"
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
    width="20"
    height="20"
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    strokeWidth="2"
  >
    <polyline points="9 18 15 12 9 6"></polyline>
  </svg>
);

type BrandType = 'Todas' | 'Honda' | 'Toyota' | 'Audi' | 'BMW' | 'Mercedes';
type CategoryType = 'Todas' | 'Hatchback' | 'Sedan' | 'SUV' | 'Pickup';

interface Car {
  name: string;
  brand: BrandType;
  category: CategoryType;
  image: string;
  isNew: boolean;
}

const allCars: Car[] = [
  // Honda
  {
    name: 'Civic',
    brand: 'Honda',
    category: 'Sedan',
    image:
      'https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEgCN7A9Yh-2oZ_-tkV79JGFyaEsanmq34zfNK9upYQ7Ma6Db8yQOG4WkUdCa_912_ekSTB_4VQymx2Vt0jZMZfaqgo59c1cypsQD4OwPFcnZazsHdyVE0eReYDZjZ3CdvlM8bmWe6Vi77mS/s2048/Honda-Civic-2021+%25285%2529.jpeg',
    isNew: true,
  },
  {
    name: 'CR-V',
    brand: 'Honda',
    category: 'SUV',
    image:
      'https://www.honda.com.br/automoveis/sites/hab/files/2025-10/crv.webp',
    isNew: false,
  },
  {
    name: 'Accord',
    brand: 'Honda',
    category: 'Sedan',
    image:
      'https://cdn.motor1.com/images/mgl/g4gO77/s1/honda-accord-advanced-hybrid-2025-br.webp',
    isNew: false,
  },
  {
    name: 'HR-V',
    brand: 'Honda',
    category: 'SUV',
    image:
      'https://www.hondacaiuas.com.br/wp-content/uploads/2025/04/honda-hr-v-2025-scaled.webp',
    isNew: true,
  },
  {
    name: 'Fit',
    brand: 'Honda',
    category: 'Hatchback',
    image:
      'https://www.webmotors.com.br/imagens/prod/347394/HONDA_FIT_1.5_PERSONAL_16V_FLEX_4P_AUTOMATICO_34739416402009260.webp?s=fill&w=170&h=125&t=true',
    isNew: false,
  },
  {
    name: 'Pilot',
    brand: 'Honda',
    category: 'SUV',
    image:
      'https://hips.hearstapps.com/hmg-prod/images/2025-honda-pilot-black-edition-01-65e1e8b47b986.jpg?crop=0.587xw:0.587xh;0.231xw,0.262xh&resize=2048:*',
    isNew: false,
  },

  // Toyota
  {
    name: 'Corolla',
    brand: 'Toyota',
    category: 'Sedan',
    image:
      'https://upload.wikimedia.org/wikipedia/commons/f/f6/Toyota_Corolla_Limousine_Hybrid_Genf_2019_1Y7A5576.jpg',
    isNew: true,
  },
  {
    name: 'RAV4',
    brand: 'Toyota',
    category: 'SUV',
    image:
      'https://www.toyotacomunica.com.br/wp-content/uploads/2024/04/Destaque-RAV4-PHEV-copiar.jpg',
    isNew: true,
  },
  {
    name: 'Camry',
    brand: 'Toyota',
    category: 'Sedan',
    image:
      'https://s2-autoesporte.glbimg.com/JR5DhkIElu7jRsAAvhvr2E7y_Ro=/0x0:620x413/600x0/smart/filters:gifv():strip_icc()/i.s3.glbimg.com/v1/AUTH_cf9d035bf26b4646b105bd958f32089d/internal_photos/bs/2020/6/z/uiNmhfTEqUPBqL6xszhA/2018-06-14-camryfrtente.jpg',
    isNew: false,
  },
  {
    name: 'Hilux',
    brand: 'Toyota',
    category: 'Pickup',
    image:
      'https://cdn.motor1.com/images/mgl/Rq6Oqm/s3/toyota-hilux-2024-mild-hybrid-48-v---esterni-in-studio.jpg',
    isNew: true,
  },
  {
    name: 'Yaris',
    brand: 'Toyota',
    category: 'Hatchback',
    image:
      'https://blog.toyotasulpar.com.br/wp-content/uploads/2022/07/yaris-2023-seda-hatch-toyota-sulpar-curitiba.jpg',
    isNew: false,
  },
  {
    name: 'Highlander',
    brand: 'Toyota',
    category: 'SUV',
    image:
      'https://www.automaistv.com.br/wp-content/uploads/2023/02/2024_Toyota_GrandHighlander_StormCloud_001_edited.jpg',
    isNew: false,
  },

  // Audi
  {
    name: 'A3 Sedan',
    brand: 'Audi',
    category: 'Sedan',
    image:
      'https://production.autoforce.com/uploads/version/profile_image/10036/comprar-performance-black-40-tfsi-s-tronic_7738837bc4.png',
    isNew: true,
  },
  {
    name: 'Q5',
    brand: 'Audi',
    category: 'SUV',
    image:
      'https://image.webmotors.com.br/_fotos/anunciousados/gigante/2025/202511/20251129/audi-q5-2.0-55-tfsie-phev-performance-quattro-s-tronic-wmimagem00595393182.jpg',
    isNew: true,
  },
  {
    name: 'A4',
    brand: 'Audi',
    category: 'Sedan',
    image:
      'https://upload.wikimedia.org/wikipedia/commons/3/35/Audi_A4_B9_sedans_%28FL%29_1X7A2441.jpg',
    isNew: false,
  },
  {
    name: 'Q3',
    brand: 'Audi',
    category: 'SUV',
    image:
      'https://www.comprecar.com.br/storage/news/featured/ef3613f1-2590-4a74-aecd-d291f5defe27.jpeg',
    isNew: false,
  },
  {
    name: 'A3 Sportback',
    brand: 'Audi',
    category: 'Hatchback',
    image:
      'https://autoentusiastas.com.br/ae/wp-content/uploads/2025/06/Audi-A3_Sportback-2025-02.jpg',
    isNew: true,
  },
  {
    name: 'Q7',
    brand: 'Audi',
    category: 'SUV',
    image: 'https://cdn.motor1.com/images/mgl/ojxeBq/s1/2025-audi-q7.jpg',
    isNew: false,
  },

  // BMW
  {
    name: 'Série 3',
    brand: 'BMW',
    category: 'Sedan',
    image:
      'https://cdn.motor1.com/images/mgl/jlwrMo/s1/novo-bmw-serie-3-2027---projecao.jpg',
    isNew: true,
  },
  {
    name: 'X3',
    brand: 'BMW',
    category: 'SUV',
    image:
      'https://imgd.aeplcdn.com/664x374/n/cw/ec/179903/x3-exterior-left-front-three-quarter.jpeg?isig=0&q=80',
    isNew: true,
  },
  {
    name: 'Série 5',
    brand: 'BMW',
    category: 'Sedan',
    image: 'https://cdn.motor1.com/images/mgl/1Z4Plp/s1/bmw-530e-2023.webp',
    isNew: false,
  },
  {
    name: 'X5',
    brand: 'BMW',
    category: 'SUV',
    image:
      'https://cdn.motor1.com/images/mgl/7kzJP/s3/2020-bmw-x5-m-competition.jpg',
    isNew: false,
  },
  {
    name: 'Série 1',
    brand: 'BMW',
    category: 'Hatchback',
    image:
      'https://www.topgear.com/sites/default/files/2024/11/P90577405_highRes_the-bmw-120.jpg',
    isNew: true,
  },
  {
    name: 'X1',
    brand: 'BMW',
    category: 'SUV',
    image:
      'https://grandbrasil.com.br/wp-content/uploads/2019/11/7_BMW_X1_Rockset.webp',
    isNew: false,
  },

  // Mercedes
  {
    name: 'Classe C',
    brand: 'Mercedes',
    category: 'Sedan',
    image:
      'https://cdn.motor1.com/images/mgl/qnAOz/s3/mercedes-c-klasse-limousine-2021.jpg',
    isNew: true,
  },
  {
    name: 'GLC',
    brand: 'Mercedes',
    category: 'SUV',
    image:
      'https://cdn.motor1.com/images/mgl/JOXpRA/s1/2023-mercedes-glc-coupe.jpg',
    isNew: true,
  },
  {
    name: 'Classe E',
    brand: 'Mercedes',
    category: 'Sedan',
    image:
      'https://cdn.motor1.com/images/mgl/E63b3z/s1/mercedes-benz-classe-e-e300-2025.webp',
    isNew: false,
  },
  {
    name: 'GLE',
    brand: 'Mercedes',
    category: 'SUV',
    image:
      'https://vehicle-images.dealerinspire.com/stock-images/thumbnails/large/chrome/fa8a965f4d1be02ca1ca1b06a8a44495.png',
    isNew: false,
  },
  {
    name: 'Classe A',
    brand: 'Mercedes',
    category: 'Hatchback',
    image:
      'https://s2-autoesporte.glbimg.com/j8ZFtpgvTvWzOSU9UE7v4Ht9SSI=/0x0:1400x911/924x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_cf9d035bf26b4646b105bd958f32089d/internal_photos/bs/2022/s/0/1P0s6BQDCxflOYYw7zSA/classea01.jpg',
    isNew: true,
  },
  {
    name: 'GLA',
    brand: 'Mercedes',
    category: 'SUV',
    image:
      'https://cdn.motor1.com/images/mgl/z2xpE/s1/4x3/mercedes-benz-gla-35-amg-2020.webp',
    isNew: false,
  },
];

const categoryOptions: CategoryType[] = [
  'Todas',
  'Sedan',
  'SUV',
  'Hatchback',
  'Pickup',
];

const ITEMS_PER_PAGE = 10;

const Navbar = () => {
  const router = useRouter();
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [activeBrand, setActiveBrand] = useState<BrandType>('Todas');
  const [selectedCategory, setSelectedCategory] =
    useState<CategoryType>('Todas');
  const [currentPage, setCurrentPage] = useState(1);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const dropdownRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (
        dropdownRef.current &&
        !dropdownRef.current.contains(event.target as Node)
      ) {
        setIsDropdownOpen(false);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => document.removeEventListener('mousedown', handleClickOutside);
  }, []);

  const filteredCars = useMemo(() => {
    return allCars.filter(car => {
      const brandMatch = activeBrand === 'Todas' || car.brand === activeBrand;
      const categoryMatch =
        selectedCategory === 'Todas' || car.category === selectedCategory;
      return brandMatch && categoryMatch;
    });
  }, [activeBrand, selectedCategory]);

  const totalPages = Math.ceil(filteredCars.length / ITEMS_PER_PAGE);
  const startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
  const endIndex = startIndex + ITEMS_PER_PAGE;
  const displayedCars = filteredCars.slice(startIndex, endIndex);
  const showingCount = Math.min(endIndex, filteredCars.length);

  const handleBrandChange = (brand: BrandType) => {
    setActiveBrand(brand);
    setCurrentPage(1);
  };

  const handleCategoryChange = (category: CategoryType) => {
    setSelectedCategory(category);
    setCurrentPage(1);
    setIsDropdownOpen(false);
  };

  const handlePreviousPage = () => {
    if (currentPage > 1) {
      setCurrentPage(currentPage - 1);
    }
  };

  const handleNextPage = () => {
    if (currentPage < totalPages) {
      setCurrentPage(currentPage + 1);
    }
  };

  const handlePageClick = (page: number) => {
    setCurrentPage(page);
  };

  const handleLoginClick = () => {
    router.push('/login');
  };

  const getCategoryLabel = (category: CategoryType) => {
    const labels: Record<CategoryType, string> = {
      Todas: 'Todas as Categorias',
      Sedan: 'Sedan',
      SUV: 'SUV',
      Hatchback: 'Hatchback',
      Pickup: 'Pickup',
    };
    return labels[category];
  };

  return (
    <>
      <Header>
        <HeaderContent>
          <HeaderLeft>
            <ModelsButton
              onClick={() => setIsMenuOpen(!isMenuOpen)}
              isOpen={isMenuOpen}
            >
              <span>Modelos e Versões</span>
              <ChevronDown />
            </ModelsButton>
          </HeaderLeft>

          <Logo>
            <Image
              src="/img/logocarcopy.png"
              alt="Honda Logo"
              width={120}
              height={40}
              priority
            />
          </Logo>

          <HeaderRight>
            <LocationInfo>
              <div>Você está em:</div>
              <strong>
                SP (São Paulo)
                <EditIcon />
              </strong>
            </LocationInfo>
            <FindDealer>
              <MapPin />
              <span>
                Encontrar uma
                <br />
                concessionária
              </span>
            </FindDealer>
            <InterestButton onClick={handleLoginClick}>Entrar</InterestButton>
          </HeaderRight>
        </HeaderContent>

        <MenuPanel isOpen={isMenuOpen}>
          <MenuContent>
            <TabsContainer>
              <Tab
                active={activeBrand === 'Todas'}
                onClick={() => handleBrandChange('Todas')}
              >
                Todas as Marcas
              </Tab>
              <Tab
                active={activeBrand === 'Honda'}
                onClick={() => handleBrandChange('Honda')}
              >
                Honda
              </Tab>
              <Tab
                active={activeBrand === 'Toyota'}
                onClick={() => handleBrandChange('Toyota')}
              >
                Toyota
              </Tab>
              <Tab
                active={activeBrand === 'Audi'}
                onClick={() => handleBrandChange('Audi')}
              >
                Audi
              </Tab>
              <Tab
                active={activeBrand === 'BMW'}
                onClick={() => handleBrandChange('BMW')}
              >
                BMW
              </Tab>
              <Tab
                active={activeBrand === 'Mercedes'}
                onClick={() => handleBrandChange('Mercedes')}
              >
                Mercedes
              </Tab>
            </TabsContainer>

            <FiltersContainer>
              <FilterDropdownWrapper ref={dropdownRef}>
                <FilterDropdown
                  isOpen={isDropdownOpen}
                  onClick={() => setIsDropdownOpen(!isDropdownOpen)}
                >
                  <span>{getCategoryLabel(selectedCategory)}</span>
                  <ChevronDown />
                </FilterDropdown>
                <DropdownList isOpen={isDropdownOpen}>
                  {categoryOptions.map(category => (
                    <DropdownOption
                      key={category}
                      isSelected={selectedCategory === category}
                      onClick={() => handleCategoryChange(category)}
                    >
                      <input
                        type="checkbox"
                        checked={selectedCategory === category}
                        readOnly
                      />
                      {getCategoryLabel(category)}
                    </DropdownOption>
                  ))}
                </DropdownList>
              </FilterDropdownWrapper>
            </FiltersContainer>

            <CarsGrid>
              {displayedCars.map((car, index) => (
                <CarCard key={index}>
                  <CarImageWrapper>
                    <CarImage src={car.image} alt={car.name} />
                    {car.isNew && <NewBadge>Novo!</NewBadge>}
                  </CarImageWrapper>
                  <CarInfo>
                    <CarName>{car.name}</CarName>
                    <ArrowIcon>
                      <ArrowRight />
                    </ArrowIcon>
                  </CarInfo>
                </CarCard>
              ))}
            </CarsGrid>

            <PaginationContainer>
              <PaginationInfo>
                Mostrando {showingCount} de {filteredCars.length}
              </PaginationInfo>
              <PaginationButtons>
                <PageButton
                  onClick={handlePreviousPage}
                  disabled={currentPage === 1}
                  isArrow
                >
                  <ChevronLeft />
                </PageButton>
                {Array.from({ length: totalPages }, (_, i) => i + 1).map(
                  page => (
                    <PageButton
                      key={page}
                      onClick={() => handlePageClick(page)}
                      active={currentPage === page}
                    >
                      {page}
                    </PageButton>
                  ),
                )}
                <PageButton
                  onClick={handleNextPage}
                  disabled={currentPage === totalPages}
                  isArrow
                >
                  <ChevronRight />
                </PageButton>
              </PaginationButtons>
            </PaginationContainer>
          </MenuContent>
        </MenuPanel>
      </Header>

      <MenuOverlay isOpen={isMenuOpen} onClick={() => setIsMenuOpen(false)} />
    </>
  );
};

export default Navbar;
