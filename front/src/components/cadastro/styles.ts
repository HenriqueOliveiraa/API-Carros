import styled from 'styled-components';

export const Container = styled.div`
  height: 100vh;
  display: flex;
  overflow: hidden;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
`;

export const LeftSection = styled.div`
  flex: 0 0 52%;
  position: relative;
  overflow: hidden;

  @media (max-width: 768px) {
    display: none;
  }
`;

export const LeftImage = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
`;

export const RightSection = styled.div`
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 2rem;
  background: white;
  position: relative;
  overflow: auto;

  @media (max-width: 768px) {
    flex: 1;
    min-width: 100%;
  }
`;

export const BackButton = styled.button`
  position: absolute;
  top: 2rem;
  left: 2rem;
  display: flex;
  align-items: center;
  justify-content: center;
  background: none;
  border: none;
  cursor: pointer;
  color: #036800ff;
  z-index: 1000;
  transition: all 0.3s;

  &:hover {
    transform: translateX(-4px);
    color: #06bd00ff;
  }
`;

export const Card = styled.div`
  width: 100%;
  max-width: 28rem;
  padding: 1rem;
  margin-top: -7rem;
`;

export const Header = styled.div`
  text-align: center;
  margin-bottom: 1.5rem;
`;

export const LogoBox = styled.div`
  display: inline-flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 0rem;

  img {
    object-fit: contain;
    border-radius: 1rem;
  }
`;

export const Title = styled.h1`
  font-size: 1.5rem;
  font-weight: bold;
  color: #444444ff;
  margin-bottom: 0rem;
  margin-top: -2.5rem;
  margin-bottom: -1.5rem;
  font-family: var(--font-cabourg);
`;

export const Subtitle = styled.p`
  color: #6b7280;
  font-family: var(--font-metropolis);
  margin: 0;
  margin-bottom: 3rem;
`;

export const FormGroup = styled.div`
  margin-bottom: 1rem;
`;

export const Label = styled.label`
  display: block;
  font-size: 0.875rem;
  font-weight: 500;
  color: #374151;
  margin-bottom: 0.5rem;
  font-family: var(--font-metropolis);
`;

export const InputWrapper = styled.div`
  position: relative;
`;

export const Input = styled.input`
  width: 100%;
  padding-left: 1rem;
  padding-right: 3rem;
  padding-top: 0.75rem;
  padding-bottom: 0.75rem;
  background: #f9fafb;
  border: 1px solid #e5e7eb;
  border-radius: 1000px;
  outline: none;
  transition: all 0.3s;
  color: #1f2937;
  font-size: 1rem;
  font-family: var(--font-metropolis);

  &::placeholder {
    color: #9ca3af;
  }

  &:focus {
    border-color: #036800ff;
  }
`;

export const ToggleButton = styled.button`
  position: absolute;
  top: 0;
  right: 0;
  padding-right: 1rem;
  height: 100%;
  display: flex;
  align-items: center;
  color: #036800ff;
  background: none;
  border: none;
  cursor: pointer;
  transition: color 0.3s;

  &:hover {
    color: #036800ff;
  }
`;

export const SubmitButton = styled.button<{ disabled?: boolean }>`
  width: 100%;
  background: linear-gradient(135deg, #059100ff 0%, #059100ff 100%);
  color: white;
  padding: 0.75rem;
  border-radius: 1000px;
  font-weight: 600;
  box-shadow: 0 10px 25px -5px rgba(102, 126, 234, 0.4);
  border: none;
  cursor: ${props => (props.disabled ? 'not-allowed' : 'pointer')};
  transition: all 0.3s;
  opacity: ${props => (props.disabled ? 0.5 : 1)};
  font-size: 1rem;
  font-family: var(--font-metropolis);
  margin-top: 0.5rem;
  margin-bottom: 0.5rem;

  &:hover:not(:disabled) {
    transform: scale(1.02);
    box-shadow: 0 15px 30px -5px rgba(102, 126, 234, 0.5);
  }

  &:active:not(:disabled) {
    transform: scale(0.98);
  }
`;

export const LoadingContent = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
`;

export const Spinner = styled.div`
  width: 1.25rem;
  height: 1.25rem;
  border: 2px solid white;
  border-top-color: transparent;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-right: 0.5rem;

  @keyframes spin {
    from {
      transform: rotate(0deg);
    }
    to {
      transform: rotate(360deg);
    }
  }
`;

export const SignUpText = styled.p`
  text-align: center;
  color: #4b5563;
  font-family: var(--font-metropolis);
  margin-top: 1rem;
  margin-bottom: 0;
`;

export const SignUpLink = styled.button`
  color: #036800ff;
  font-weight: 600;
  background: none;
  border: none;
  cursor: pointer;
  transition: color 0.3s;
  font-family: var(--font-metropolis);

  &:hover {
    color: #06bd00ff;
  }
`;

export const ErrorMessage = styled.span`
  color: #ef4444;
  font-size: 0.875rem;
  margin-top: 0.25rem;
  display: block;
  font-family: var(--font-metropolis);
`;
