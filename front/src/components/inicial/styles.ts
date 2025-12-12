import styled from 'styled-components';

export const Container = styled.div`
  min-height: 100vh;
  background: #ffffff;
  margin-left: 260px;
  padding: 0;

  @media (max-width: 768px) {
    margin-left: 0;
  }
`;

export const HeroSection = styled.section`
  position: relative;
  height: 60vh;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  background: #ffffff;
  overflow: hidden;

  @media (max-width: 768px) {
    height: 50vh;
  }
`;

export const HeroContent = styled.div`
  position: relative;
  z-index: 3;
  padding: 200px 80px 50px;
  max-width: 600px;
  margin-left: -2rem;
  transform: translateY(150px);

  @media (max-width: 768px) {
    padding: 80px 30px 30px;
    transform: translateY(80px);
  }
`;

export const HeroTitle = styled.h1`
  font-size: 4.5rem;
  font-weight: 700;
  font-family: var(--font-cabourg);
  color: #1a1a1a;
  margin-bottom: 20px;
  letter-spacing: -2px;
  line-height: 1.1;
  white-space: nowrap;
  margin-top: 1rem;

  @media (max-width: 768px) {
    font-size: 2.5rem;
  }
`;

export const HeroButtons = styled.div`
  display: flex;
  gap: 20px;
  align-items: stretch;

  @media (max-width: 768px) {
    flex-direction: column;
  }
`;

export const Button = styled.button`
  padding: 12px 30px;
  font-size: 1rem;
  font-weight: 500;
  color: #036800ff;
  background: transparent;
  border: 1px solid #036800ff;
  cursor: pointer;
  transition: all 0.3s ease;
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  white-space: nowrap;
  height: 48px;

  &:hover {
    background: #06bd00ff;
    border-color: #06bd00ff;
    color: white;
    transform: translateY(-2px);
    box-shadow: 0 10px 25px rgba(0, 0, 0, 0.15);
  }

  @media (max-width: 768px) {
    width: 100%;
  }
`;

export const ButtonPrimary = styled.button`
  padding: 12px 30px;
  font-size: 1rem;
  font-weight: 500;
  color: white;
  background: #06bd00ff;
  border: 1px solid #06bd00ff;
  cursor: pointer;
  transition: all 0.3s ease;
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  white-space: nowrap;
  height: 48px;

  &:hover {
    background: #036800ff;
    border-color: #036800ff;
    transform: translateY(-2px);
    box-shadow: 0 10px 25px rgba(0, 204, 0, 0.3);
  }

  @media (max-width: 768px) {
    width: 100%;
  }
`;

export const CarImage = styled.img`
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  z-index: 1;

  @media (max-width: 768px) {
    height: 100%;
  }
`;

export const ImageOverlay = styled.div`
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  z-index: 2;

  @media (max-width: 768px) {
    height: 100%;
  }
`;

export const SpecsSection = styled.section`
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  padding: 60px 40px;
  background: white;
  position: relative;
  z-index: 10;

  @media (max-width: 1200px) {
    grid-template-columns: repeat(2, 1fr);
    gap: 30px;
    padding: 50px 40px;
  }

  @media (max-width: 768px) {
    padding: 40px 20px;
    gap: 25px;
  }
`;

export const SpecCard = styled.div`
  text-align: center;
  padding: 20px 10px;
  transition: transform 0.3s ease;
  margin-top: 5rem;

  &:hover {
    transform: translateY(-5px);
  }
`;

export const SpecLabel = styled.p`
  font-size: 0.75rem;
  font-family: var(--font-metropolis);
  font-weight: 500;
  color: #666;
  margin-bottom: 8px;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  text-align: left;

  @media (max-width: 768px) {
    font-size: 0.7rem;
  }
`;

export const SpecValue = styled.h3`
  font-size: 2.2rem;
  font-weight: 500;
  font-family: var(--font-metropolis);
  font-weight: 300;
  color: #1a1a1a;
  margin: 8px 0;
  line-height: 1.2;
  text-align: left;

  @media (max-width: 1200px) {
    font-size: 2rem;
  }

  @media (max-width: 768px) {
    font-size: 1.8rem;
  }
`;

export const CarouselSection = styled.section`
  padding: 80px 0;
  background: #ffffff;
  position: relative;
`;

export const CarouselContainer = styled.div`
  position: relative;
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 80px;

  @media (max-width: 968px) {
    padding: 0 40px;
  }
`;

export const CarouselWrapper = styled.div`
  overflow: hidden;
  width: 100%;
`;

export const CarouselTrack = styled.div`
  display: flex;
  transition: transform 0.5s ease-in-out;
  gap: 20px;
`;

export const CarouselCard = styled.div`
  min-width: calc(50% - 10px);
  position: relative;
  background: #f5f5f5;
  display: flex;
  flex-direction: column;

  @media (max-width: 968px) {
    min-width: 100%;
  }
`;

export const CarouselImage = styled.img`
  width: 100%;
  height: auto;
  aspect-ratio: 16/11;
  object-fit: cover;
  transition: transform 0.6s ease;

  ${CarouselCard}:hover & {
    transform: scale(1.05);
  }
`;

export const CarouselContent = styled.div`
  padding: 30px 40px;
  background: white;
  color: #1a1a1a;

  @media (max-width: 768px) {
    padding: 20px;
  }
`;

export const CarouselTitle = styled.h3`
  font-size: 1.5rem;
  font-weight: 500;
  margin-bottom: 12px;
  font-family: var(--font-cabourg);
  margin-left: -2.5rem;
  color: #1a1a1a;

  @media (max-width: 768px) {
    font-size: 1.3rem;
  }
`;

export const CarouselDescription = styled.p`
  font-size: 0.95rem;
  line-height: 1.6;
  color: #666;
  font-family: var(--font-metropolis);
  margin-left: -2.5rem;

  @media (max-width: 768px) {
    font-size: 0.85rem;
  }
`;

export const CarouselButton = styled.button`
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background: white;
  border: none;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  z-index: 10;

  &:hover {
    background: #f5f5f5;
    transform: translateY(-50%) scale(1.1);
    box-shadow: 0 6px 16px rgba(0, 0, 0, 0.2);
  }

  svg {
    color: #1a1a1a;
  }

  @media (max-width: 968px) {
    width: 40px;
    height: 40px;

    svg {
      width: 20px;
      height: 20px;
    }
  }
`;

export const CarouselButtonLeft = styled(CarouselButton)`
  left: 10px;
  margin-top: -6rem;
`;

export const CarouselButtonRight = styled(CarouselButton)`
  right: 10px;
  margin-top: -6rem;
`;

export const CarouselDotsContainer = styled.div`
  display: flex;
  justify-content: center;
  gap: 12px;
  margin-top: 40px;
`;

export const CarouselDot = styled.button<{ active?: boolean }>`
  width: 12px;
  height: 12px;
  border-radius: 50%;
  background: ${props => (props.active ? '#06bd00ff' : '#d0d0d0')};
  border: none;
  cursor: pointer;
  transition: all 0.3s ease;

  &:hover {
    background: #06bd00ff;
  }
`;

export const FeaturesSection = styled.section`
  padding: 80px 0;
  background: #f8f9fa;
`;

export const FeatureGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 0;

  @media (max-width: 968px) {
    grid-template-columns: 1fr;
  }
`;

export const FeatureCard = styled.div`
  position: relative;
  overflow: hidden;
  aspect-ratio: 16/9;

  &:hover img {
    transform: scale(1.05);
  }
`;

export const FeatureImage = styled.div`
  width: 100%;
  height: 100%;
  transition: transform 0.6s ease;
`;

export const FeatureContent = styled.div`
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  padding: 40px;
  background: linear-gradient(to top, rgba(0, 0, 0, 0.8) 0%, transparent 100%);
  color: white;
`;

export const FeatureTitle = styled.h3`
  font-size: 1.8rem;
  font-weight: 400;
  margin-bottom: 15px;
`;

export const FeatureDescription = styled.p`
  font-size: 1rem;
  line-height: 1.6;
  opacity: 0.9;
`;

export const CarouselDots = styled.div`
  display: flex;
  justify-content: center;
  gap: 12px;
  padding: 40px 0;
`;

export const Dot = styled.button<{ active?: boolean }>`
  width: ${props => (props.active ? '40px' : '12px')};
  height: 12px;
  border-radius: 6px;
  background: ${props => (props.active ? '#1a1a1a' : '#ccc')};
  border: none;
  cursor: pointer;
  transition: all 0.3s ease;

  &:hover {
    background: #1a1a1a;
  }
`;
