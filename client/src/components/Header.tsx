import { Button, Container } from "@radix-ui/themes";
import { Link } from "react-router-dom";

function Header() {
  return (
    <header className="w-full border-b py-4">
      <Container className="px-10">
        <div className="flex justify-between items-center">
          <Button
            asChild
            className="font-medium"
            size={"3"}
            variant="ghost"
            highContrast
          >
            <Link to="/">Home</Link>
          </Button>
          <nav>
            <Button
              asChild
              className="font-medium"
              size={"3"}
              variant="ghost"
              highContrast
            >
              <Link to="/dashboard">Dashboard</Link>
            </Button>
          </nav>
        </div>
      </Container>
    </header>
  );
}

export default Header;
