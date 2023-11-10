import { ArrowLeftIcon } from "@radix-ui/react-icons";
import { Button, Container } from "@radix-ui/themes";
import { Link, useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { CarDetail, Price } from "@/types/interfaces";
import ReservationForm from "@/components/ReservationForm";

function Reservation() {
  const { id } = useParams();
  const [carPrice, setCarPrice] = useState<Price>();

  const navigate = useNavigate();

  useEffect(() => {
    fetch(`http://localhost:8080/api/cars/${id}`)
      .then((res) => {
        if (res.ok) {
          return res.json();
        }
      })
      .then((data: CarDetail) => setCarPrice(data.price));
  }, [id]);

  return (
    <Container className="p-4">
      <Button asChild variant="ghost">
        <Link to={`..`} relative="path" className="flex items-center gap-1">
          <ArrowLeftIcon />
          Back to car
        </Link>
      </Button>
      <div className="sm:grid gap-5 grid-cols-1 md:grid-cols-3 py-4">
        {carPrice && (
          <ReservationForm
            carId={Number(id)}
            carPrice={carPrice}
            onSuccessfulPost={() =>
              setTimeout(() => {
                navigate("/");
              }, 3000)
            }
          />
        )}
      </div>
    </Container>
  );
}

export default Reservation;
