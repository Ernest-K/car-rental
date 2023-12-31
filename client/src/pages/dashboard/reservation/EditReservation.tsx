import EditReservationForm from "@/components/EditReservationForm";
import { ArrowLeftIcon } from "@radix-ui/react-icons";
import { Button, Container } from "@radix-ui/themes";
import { Link } from "react-router-dom";

function EditReservation() {
  return (
    <Container className="p-4">
      <Button asChild variant="ghost">
        <Link
          to={"/dashboard"}
          relative="path"
          className="flex items-center gap-1"
        >
          <ArrowLeftIcon />
          Back to dashboard
        </Link>
      </Button>
      <div className="sm:grid gap-5 grid-cols-1 md:grid-cols-3 py-4">
        <EditReservationForm />
      </div>
    </Container>
  );
}

export default EditReservation;
