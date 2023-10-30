import { ArrowLeftIcon } from "@radix-ui/react-icons";
import { Button, Container } from "@radix-ui/themes";
import { Link, useParams } from "react-router-dom";

function Reservation() {
  const { id } = useParams();

  return (
    <Container className="p-4">
      <Button asChild variant="ghost">
        <Link to={`..`} relative="path" className="flex items-center gap-1">
          <ArrowLeftIcon />
          Back to car
        </Link>
      </Button>
    </Container>
  );
}

export default Reservation;
