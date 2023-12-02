import EditCarForm from "@/components/EditCarForm";
import { ArrowLeftIcon } from "@radix-ui/react-icons";
import { Button, Container } from "@radix-ui/themes";
import { Link } from "react-router-dom";

function EditCar() {
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
      <div className="sm:grid gap-5 grid-cols-1 md:px-20 py-4">
        <EditCarForm />
      </div>
    </Container>
  );
}

export default EditCar;
