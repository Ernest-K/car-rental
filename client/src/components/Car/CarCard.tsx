import { AspectRatio, Button, Card } from "@radix-ui/themes";
import { Car } from "../../types/interfaces";
import {
  CheckCircledIcon,
  CrossCircledIcon,
  InfoCircledIcon,
} from "@radix-ui/react-icons";
import { Link } from "react-router-dom";
import carImg from "../../assets/example.jpg";

interface CarCardProps {
  data: Car;
}

function CarCard({ data }: CarCardProps) {
  const { id, status, make, model, price, category } = data;

  return (
    <Card variant="classic">
      <div className="flex justify-end items-center">
        {status === "UNAVAILABLE" ? (
          <CrossCircledIcon className="w-4 h-4 mr-1 text-red-600" />
        ) : (
          <CheckCircledIcon className="w-4 h-4 mr-1 text-green-600" />
        )}
        <p className="capitalize">{status.toLowerCase()}</p>
      </div>
      <div className="my-2">
        <AspectRatio ratio={16 / 8}>
          <img
            src={carImg}
            alt="Car photo"
            style={{
              objectFit: "cover",
              width: "100%",
              height: "100%",
              borderRadius: "var(--radius-4)",
            }}
          />
        </AspectRatio>
      </div>
      <p className="text-xl font-bold">{make}</p>
      <p className="text-lg font-medium">{model}</p>
      <div className="flex justify-between items-center">
        <p className="text-sm">Category: </p>
        <p className="capitalize">{category.name}</p>
      </div>
      <div className="flex justify-between items-center">
        <p className="text-sm">Price: </p>
        <p>
          <span className="opacity-70 text-sm"> from </span>
          {price.forWeek}
          <span className="text-sm"> [PLN]</span>
        </p>
      </div>
      <div className="flex justify-between items-center mt-2">
        <Button asChild variant="outline">
          <Link to={`/cars/${id}`}>
            Details <InfoCircledIcon width="16" height="16" />
          </Link>
        </Button>
        <Button
          disabled={status === "UNAVAILABLE"}
          asChild={status !== "UNAVAILABLE"}
        >
          {status === "UNAVAILABLE" ? (
            "Reserve"
          ) : (
            <Link to={`/cars/${id}/reservation`}>Reserve</Link>
          )}
        </Button>
      </div>
    </Card>
  );
}

export default CarCard;
