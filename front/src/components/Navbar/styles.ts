import styled from 'styled-components';

export const Header = styled.header`
  background: white;
  border-bottom: 1px solid #e5e7eb;
  position: sticky;
  top: 0;
  z-index: 50;
`;

export const HeaderContent = styled.div`
  max-width: 1280px;
  margin: 0 auto;
  padding: 0 2rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 70px;
  overflow: visible;
  position: relative;
`;

export const HeaderLeft = styled.div`
  flex: 1;
  position: relative;
`;

export const ModelsButton = styled.button<{ isOpen: boolean }>`
  display: flex;
  align-items: center;
  gap: 0.5rem;
  font-size: 18px;
  color: #6b7280;
  background: none;
  border: none;
  cursor: pointer;
  transition: color 0.2s;
  padding: 0.5rem;
  font-weight: 700;
  font-family: var(--font-cabourg);

  &:hover {
    color: #059100ff;
  }

  svg {
    transition: transform 0.2s;
    transform: ${props => (props.isOpen ? 'rotate(180deg)' : 'rotate(0deg)')};
  }
`;

export const Logo = styled.div`
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  flex-shrink: 0;
  display: flex;
  align-items: center;
  height: 100%;

  img {
    height: 140px;
    width: auto;
    object-fit: contain;
    margin-top: -10px;
  }
`;

export const HeaderRight = styled.div`
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: flex-end;
  gap: 1rem;
`;

export const LocationInfo = styled.div`
  font-size: 0.85rem;
  font-family: var(--font-metropolis);
  text-align: right;
  line-height: 1.4;

  div:first-child {
    color: #6b7280;
    text-align: left;
  }

  strong {
    font-weight: 600;
    color: #059100ff;
    display: flex;
    align-items: center;
    gap: 0.3rem;
    justify-content: flex-end;

    svg {
      color: #6b7280;
    }
  }
`;

export const FindDealer = styled.button`
  display: flex;
  align-items: center;
  gap: 0.5rem;
  font-size: 0.875rem;
  font-family: var(--font-metropolis);
  color: #111827;
  background: none;
  border: 1px solid #d1d5db;
  cursor: pointer;
  transition: all 0.2s;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  font-weight: 500;
  height: 50px;
  width: 170px;
  line-height: 1.3;

  svg {
    color: #059100ff;
    flex-shrink: 0;
  }

  span {
    text-align: left;
    line-height: 1.3;
  }

  &:hover {
    border-color: #d1d5db;
    background: #f9fff9ff;
  }
`;

export const InterestButton = styled.button`
  background: #059100ff;
  color: white;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  border: none;
  cursor: pointer;
  font-size: 0.875rem;
  font-family: var(--font-metropolis);
  transition: background 0.2s;
  font-weight: 600;
  height: 50px;
  width: 170px;
  white-space: nowrap;

  &:hover {
    background: #06bd00ff;
  }
`;

export const MenuOverlay = styled.div<{ isOpen: boolean }>`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  z-index: 40;
  opacity: ${props => (props.isOpen ? 1 : 0)};
  visibility: ${props => (props.isOpen ? 'visible' : 'hidden')};
  transition: opacity 0.3s, visibility 0.3s;
`;

export const MenuPanel = styled.div<{ isOpen: boolean }>`
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  background: white;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
  max-height: ${props => (props.isOpen ? '800px' : '0')};
  overflow: hidden;
  transition: max-height 0.3s ease-in-out;
`;

export const MenuContent = styled.div`
  max-width: 1280px;
  margin: 0 auto;
  padding: 2rem;
`;

export const TabsContainer = styled.div`
  display: flex;
  gap: 2rem;
  border-bottom: 2px solid #e5e7eb;
  margin-bottom: 2rem;
  margin-top: -2rem;
`;

export const Tab = styled.button<{ active: boolean }>`
  background: none;
  border: none;
  padding: 1rem 0;
  font-size: 1rem;
  font-weight: 500;
  color: ${props => (props.active ? '#059100ff' : '#6b7280')};
  border-bottom: 2px solid
    ${props => (props.active ? '#059100ff' : 'transparent')};
  margin-bottom: -2px;
  cursor: pointer;
  font-family: var(--font-metropolis);
  transition: color 0.2s;

  &:hover {
    color: #06bd00ff;
  }
`;

export const FiltersContainer = styled.div`
  display: flex;
  gap: 1rem;
  margin-bottom: 3.5rem;
  margin-top: -1rem;
`;

export const FilterDropdownWrapper = styled.div`
  position: relative;
  min-width: 280px;
`;

export const FilterDropdown = styled.div<{ isOpen: boolean }>`
  position: relative;
  padding: 0.875rem 1rem;
  border: 1px solid #d1d5db;
  border-radius: 1000px;
  font-family: var(--font-metropolis);
  font-size: 16px;
  color: #111827;
  background: white;
  cursor: pointer;
  transition: all 0.2s;
  min-width: 280px;
  font-weight: 500;
  display: flex;
  align-items: center;
  justify-content: space-between;
  user-select: none;

  &:hover {
    border-color: #9ca3af;
  }

  svg {
    flex-shrink: 0;
    width: 20px;
    height: 20px;
    margin-left: 0.75rem;
    transition: transform 0.2s;
    transform: ${props => (props.isOpen ? 'rotate(180deg)' : 'rotate(0deg)')};
    color: #059100ff;
  }
`;

export const DropdownList = styled.div<{ isOpen: boolean }>`
  position: absolute;
  top: calc(100% + 8px);
  left: 0;
  right: 0;
  background: white;
  border: 1px solid #d1d5db;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  max-height: ${props => (props.isOpen ? '300px' : '0')};
  opacity: ${props => (props.isOpen ? 1 : 0)};
  visibility: ${props => (props.isOpen ? 'visible' : 'hidden')};
  transition: all 0.3s ease;
  z-index: 100;
`;

export const DropdownOption = styled.div<{ isSelected: boolean }>`
  padding: 0.875rem 1rem;
  font-family: var(--font-metropolis);
  font-size: 16px;
  color: ${props => (props.isSelected ? '#059100ff' : '#111827')};
  background: ${props => (props.isSelected ? '#f0fdf4' : 'white')};
  cursor: pointer;
  transition: all 0.2s;
  border-bottom: 1px solid #f3f4f6;
  font-weight: ${props => (props.isSelected ? '600' : '400')};
  display: flex;
  align-items: center;

  &:last-child {
    border-bottom: none;
  }

  &:hover {
    background: #f9fafb;
  }

  input[type='checkbox'] {
    margin-right: 0.75rem;
    width: 18px;
    height: 18px;
    border: 2px solid #d1d5db;
    border-radius: 4px;
    cursor: pointer;
    appearance: none;
    position: relative;
    flex-shrink: 0;

    &:checked {
      background: #059100ff;
      border-color: #059100ff;
    }

    &:checked::after {
      content: '✓';
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      color: white;
      font-size: 12px;
      font-weight: bold;
    }
  }
`;

export const CarsGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 3rem;
  row-gap: 1rem;
  justify-items: center;
`;

export const CarCard = styled.div`
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0;
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.2s;
  width: 100%;
  max-width: 450px;

  &:hover {
    background: #f9fafb;
  }
`;

export const CarImageWrapper = styled.div`
  position: relative;
  width: 140px;
  height: 90px;
  flex-shrink: 0;
  overflow: hidden;
  border-radius: 4px;
`;

export const CarImage = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 4px;
`;

export const NewBadge = styled.span`
  position: absolute;
  font-family: var(--font-metropolis);
  top: 0px;
  left: 0;
  background: #b60000ff;
  color: white;
  padding: 0.4rem 0.5rem;
  border-radius: 0 4px 4px 0;
  font-size: 0.85rem;
  font-weight: 600;
`;

export const CarInfo = styled.div`
  flex: 1;
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 0.25rem;
  padding: 0 0.5rem;
`;

export const CarName = styled.h3`
  font-size: 1.125rem;
  font-family: var(--font-metropolis);
  font-weight: 600;
  color: #111827;
  margin: 0;
`;

export const ArrowIcon = styled.div`
  color: #059100ff;
  display: flex;
  align-items: center;
  flex-shrink: 0;
`;

export const PaginationContainer = styled.div`
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 1rem;
  margin-top: 1.5rem;
  padding-top: 1rem;
  border-top: 1px solid #e5e7eb;
`;

export const PaginationInfo = styled.div`
  font-family: var(--font-metropolis);
  font-size: 16px;
  color: #6b7280;
  font-weight: 500;
`;

export const PaginationButtons = styled.div`
  display: flex;
  align-items: center;
  gap: 0.5rem;
`;

export const PageButton = styled.button<{
  active?: boolean;
  disabled?: boolean;
  isArrow?: boolean;
}>`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 36px;
  height: 36px;
  border-radius: 50%;
  border: none;
  background: ${props => {
    if (props.active) return '#059100ff';
    if (props.disabled) return 'transparent';
    return 'transparent';
  }};
  color: ${props => {
    if (props.active) return 'white';
    if (props.disabled) return '#b8b8b8';
    if (props.isArrow) return '#06bd00ff';
    return '#6b7280';
  }};
  font-family: var(--font-metropolis);
  font-size: 16px;
  font-weight: ${props => (props.active ? '600' : '500')};
  cursor: ${props => (props.disabled ? 'not-allowed' : 'pointer')};
  transition: all 0.2s;

  svg {
    width: 20px;
    height: 20px;
  }

  &:hover:not(:disabled) {
    background: ${props => (props.active ? '#059100ff' : '#f3f4f6')};
  }
`;
