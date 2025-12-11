import styled from 'styled-components';

export const Container = styled.div`
  min-height: 100vh;
  background: white;
`;

export const CarouselContainer = styled.div`
  position: relative;
  height: 600px;
  overflow: hidden;
  width: 100%;
`;

export const Slide = styled.div<{ $isActive: boolean }>`
  position: absolute;
  inset: 0;
  opacity: ${props => (props.$isActive ? 1 : 0)};
  transition: opacity 0.7s;
  width: 100%;
  height: 100%;
`;

export const SlideBackground = styled.div<{ $bgImage: string }>`
  position: absolute;
  inset: 0;
  background-image: url(${props => props.$bgImage});
  background-size: cover;
  background-position: center center;
  background-repeat: no-repeat;
  width: 100%;
  height: 100%;

  &::before {
    content: '';
    position: absolute;
    inset: 0;
    background: linear-gradient(
      135deg,
      rgba(0, 0, 0, 0.6) 0%,
      rgba(0, 0, 0, 0.3) 50%,
      rgba(0, 0, 0, 0.5) 100%
    );
  }
`;

export const SlideContent = styled.div`
  position: relative;
  max-width: 1280px;
  margin: 0 auto;
  padding: 0 2rem;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  gap: 2rem;
`;

export const TextContent = styled.div`
  color: white;
  max-width: 40rem;
  flex-shrink: 0;
  z-index: 2;
  text-align: left;
`;

export const Subtitle = styled.p`
  font-size: 1.25rem;
  font-family: var(--font-cabourg);
  font-weight: 700;
  margin-bottom: 0.5rem;
  letter-spacing: 0.4em;
  text-transform: uppercase;
  text-shadow: 2px 2px 8px rgba(0, 0, 0, 0.5);
  color: #ffffff;
  animation: fadeInUp 0.8s ease-out;

  @keyframes fadeInUp {
    from {
      opacity: 0;
      transform: translateY(20px);
    }
    to {
      opacity: 1;
      transform: translateY(0);
    }
  }
`;

export const Title = styled.h1`
  font-size: 7rem;
  font-family: var(--font-cabourg);
  font-weight: 900;
  margin-bottom: 0.5rem;
  letter-spacing: 0.05em;
  line-height: 0.9;
  text-shadow: 3px 3px 12px rgba(0, 0, 0, 0.6);
  color: #ffffff;
  background: linear-gradient(135deg, #ffffff 0%, #e0e0e0 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  animation: fadeInScale 1s ease-out;

  @keyframes fadeInScale {
    from {
      opacity: 0;
      transform: scale(0.9);
    }
    to {
      opacity: 1;
      transform: scale(1);
    }
  }

  @media (max-width: 768px) {
    font-size: 4rem;
  }
`;

export const Tagline = styled.p`
  font-size: 2.5rem;
  font-family: var(--font-cabourg);
  margin-bottom: 1rem;
  font-weight: 400;
  letter-spacing: 0.08em;
  text-shadow: 2px 2px 10px rgba(0, 0, 0, 0.5);
  color: #f0f0f0;
  animation: fadeInSlide 1.2s ease-out;

  @keyframes fadeInSlide {
    from {
      opacity: 0;
      transform: translateX(-30px);
    }
    to {
      opacity: 1;
      transform: translateX(0);
    }
  }

  @media (max-width: 768px) {
    font-size: 1.5rem;
  }
`;

export const BadgeContainer = styled.div`
  position: absolute;
  left: 2rem;
  bottom: 2rem;
  z-index: 2;

  img {
    height: 80px;
    width: auto;
  }

  svg {
    height: 80px;
    width: auto;
  }
`;

export const NavButton = styled.button<{ $position: 'left' | 'right' }>`
  position: absolute;
  ${props => props.$position}: 2rem;
  top: 50%;
  transform: translateY(-50%);
  width: 3rem;
  height: 3rem;
  background: rgba(0, 0, 0, 0.5);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  border: none;
  cursor: pointer;
  transition: background 0.2s;
  z-index: 10;

  &:hover {
    background: rgba(0, 0, 0, 0.7);
  }
`;

export const DotsContainer = styled.div`
  position: absolute;
  bottom: 2rem;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  gap: 0.5rem;
  z-index: 10;
`;

export const Dot = styled.button<{ $isActive: boolean }>`
  width: 0.5rem;
  height: 0.5rem;
  border-radius: 50%;
  background: ${props =>
    props.$isActive ? '#059100ff' : 'rgba(255, 255, 255, 0.5)'};
  border: none;
  cursor: pointer;
  transition: all 0.3s;

  &:hover {
    background: ${props =>
      props.$isActive ? '#059100ff' : 'rgba(255, 255, 255, 0.8)'};
  }
`;

export const ShareButton = styled.button`
  position: absolute;
  top: 2rem;
  right: 2rem;
  width: 2.5rem;
  height: 2.5rem;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  border: none;
  cursor: pointer;
  transition: background 0.2s;
  z-index: 10;

  &:hover {
    background: rgba(255, 255, 255, 0.3);
  }
`;

export const CardsSection = styled.section`
  max-width: 1280px;
  margin: 0 auto;
  padding: 4rem 2rem;
`;

export const SectionTitle = styled.h2`
  font-size: 2.75rem;
  font-family: var(--font-cabourg);
  font-weight: 700;
  text-align: center;
  margin-bottom: 1rem;
`;

export const CategoryNav = styled.div`
  display: flex;
  justify-content: center;
  gap: 2rem;
  margin: 2rem 0;
`;

export const CategoryButton = styled.button<{ $isActive: boolean }>`
  font-size: 1.125rem;
  font-family: var(--font-metropolis);
  font-weight: ${props => (props.$isActive ? '600' : '400')};
  color: ${props => (props.$isActive ? '#000' : '#6b7280')};
  background: none;
  border: none;
  border-bottom: 2px solid
    ${props => (props.$isActive ? '#059100ff' : 'transparent')};
  padding-bottom: 0.5rem;
  cursor: pointer;
  transition: color 0.2s;

  &:hover {
    color: #111827;
  }
`;

export const CardsSliderContainer = styled.div`
  position: relative;
  margin-top: 3rem;
  overflow: hidden;
`;

export const CardsSliderWrapper = styled.div`
  width: 100%;
  overflow: hidden;
`;

export const CardsSlideTrack = styled.div<{
  $translateX: number;
  $cardsPerView: number;
}>`
  display: flex;
  transition: transform 0.5s ease-in-out;
  transform: translateX(
    calc(
      -${props => props.$translateX * 100}% - ${props => props.$translateX * 1.5}rem
    )
  );
  gap: 1.5rem;
`;

export const CardsNavButton = styled.button<{ $position: 'left' | 'right' }>`
  position: absolute;
  ${props => props.$position}: 0rem;
  top: 50%;
  transform: translateY(-180%);
  width: 3rem;
  height: 3rem;
  background: rgba(0, 0, 0, 0.5);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  border: none;
  cursor: pointer;
  transition: background 0.2s;
  z-index: 10;

  &:hover {
    background: rgba(0, 0, 0, 0.7);
  }
`;

export const CardsGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 1.5rem;
  margin-top: 3rem;

  @media (min-width: 768px) {
    grid-template-columns: repeat(2, 1fr);
  }

  @media (min-width: 1024px) {
    grid-template-columns: repeat(4, 1fr);
  }
`;

export const CarCard = styled.div`
  background: white;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: box-shadow 0.3s;
  flex: 0 0 calc(25% - 1.125rem);
  max-width: calc(25% - 1.125rem);

  &:hover {
    box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.15);

    img {
      transform: scale(1.1);
    }
  }
`;

export const CardImage = styled.div`
  position: relative;
  overflow: hidden;

  img {
    width: 100%;
    height: 16rem;
    object-fit: cover;
    transition: transform 0.5s;
  }
`;

export const NewBadge = styled.div`
  position: absolute;
  font-family: var(--font-metropolis);
  top: 0rem;
  left: 0rem;
  background: #b60000ff;
  color: white;
  padding: 0.55rem 0.75rem;
  font-size: 0.85rem;
  font-weight: 600;
  border-radius: 4px;
  z-index: 10;
`;

export const CardContent = styled.div`
  padding: 1.5rem;
`;

export const CarName = styled.h3`
  font-size: 1.5rem;
  font-family: var(--font-metropolis);
  font-weight: 700;
  margin-bottom: 0.5rem;
`;

export const CarTagline = styled.p`
  color: #6b7280;
  font-family: var(--font-metropolis);
  margin-bottom: 1rem;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`;

export const CardActions = styled.div`
  display: flex;
  gap: 1rem;
  flex-wrap: wrap;
`;

export const ActionLink = styled.button`
  display: flex;
  font-family: var(--font-metropolis);
  align-items: center;
  gap: 0.25rem;
  color: #059100ff;
  font-weight: 600;
  background: none;
  border: none;
  cursor: pointer;
  transition: text-decoration 0.2s;

  &:hover {
    text-decoration: underline;
  }
`;

export const CardsDots = styled.div`
  display: flex;
  justify-content: center;
  gap: 0.5rem;
  margin-top: 2rem;
`;

export const CardDot = styled.button<{ $isActive: boolean }>`
  width: 0.5rem;
  height: 0.5rem;
  border-radius: 50%;
  background: ${props => (props.$isActive ? '#1f2937' : '#d1d5db')};
  border: none;
  cursor: pointer;
  transition: all 0.3s;

  &:hover {
    background: ${props => (props.$isActive ? '#1f2937' : '#9ca3af')};
  }
`;

export const ServiceSection = styled.section`
  position: relative;
  height: 500px;
  overflow: hidden;
  width: 100%;
  margin: 0;
  padding: 0;
`;

export const ServiceImage = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
  transition: transform 0.3s ease;
  transform: scale(1.1);
`;

export const ServiceOverlay = styled.div`
  position: absolute;
  inset: 0;
  background: linear-gradient(
    to top,
    rgba(0, 0, 0, 0.85) 0%,
    rgba(0, 0, 0, 0.9) 20%,
    rgba(0, 0, 0, 0.9) 50%,
    transparent 100%
  );
  display: flex;
  align-items: flex-end;
  justify-content: center;
  padding: 3rem 2rem;
`;

export const ServiceGrid = styled.div`
  display: grid;
  grid-template-columns: 1fr auto 1fr auto 1fr;
  gap: 0;
  max-width: 1280px;
  width: 100%;
  align-items: center;

  @media (max-width: 768px) {
    grid-template-columns: 1fr;
    gap: 2rem;
  }
`;

export const ServiceCard = styled.div`
  color: white;
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 1.5rem;
  gap: 0.75rem;
`;

export const ServiceTitle = styled.h3`
  font-size: 24px;
  font-family: var(--font-cabourg);
  font-weight: 700;
  margin-bottom: 0.5rem;
`;

export const ServiceBadge = styled.span`
  background: #4caf50;
  color: white;
  padding: 0.25rem 0.75rem;
  border-radius: 12px;
  font-size: 0.75rem;
  font-weight: 600;
  margin-bottom: 1rem;
  font-family: var(--font-metropolis);
`;

export const ServiceDescription = styled.p`
  font-size: 0.95rem;
  font-family: var(--font-metropolis);
  margin-bottom: 1rem;
  line-height: 1.5;
  max-width: 280px;
`;

export const ServiceButton = styled.button`
  background: #059100ff;
  color: white;
  border-radius: 8px;
  border: none;
  padding: 0.75rem 2rem;
  font-size: 0.95rem;
  font-weight: 500;
  font-family: var(--font-metropolis);
  cursor: pointer;
  transition: background 0.3s;

  &:hover {
    background: #06bd00ff;
  }
`;

export const ServiceDivider = styled.div`
  width: 1px;
  height: 150px;
  background: rgba(255, 255, 255, 0.3);

  @media (max-width: 768px) {
    display: none;
  }
`;

export const Footer = styled.footer`
  background: #111827;
  color: white;
  padding: 1rem 0;
`;

export const FooterContent = styled.div`
  max-width: 1280px;
  margin: 0 auto;
  padding: 0 2rem;
  display: flex;
  gap: 1rem;
`;

export const NoticeIcon = styled.div`
  flex-shrink: 0;
  width: 3rem;
  height: 3rem;
  border: 2px solid white;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
`;

export const NoticeTitle = styled.h3`
  font-size: 1.125rem;
  font-weight: 700;
  margin-bottom: 0.5rem;
`;

export const NoticeText = styled.div`
  font-size: 0.875rem;
  color: #d1d5db;

  p {
    margin-bottom: 0.5rem;

    strong {
      font-weight: 600;
    }
  }
`;
