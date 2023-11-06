import { Calendar } from "@/components/ui/calendar";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import { cn } from "@/lib/utils";
import * as Form from "@radix-ui/react-form";
import { ArrowLeftIcon, CalendarIcon } from "@radix-ui/react-icons";
import { Button, Container, TextFieldInput } from "@radix-ui/themes";
import { format } from "date-fns";
import { Link, useNavigate, useParams } from "react-router-dom";
import { useForm, SubmitHandler, Controller } from "react-hook-form";
import { useEffect, useState } from "react";
import { CarDetailInfo, Price } from "@/types/interfaces";
import { useToast } from "@/components/ui/use-toast";

type ReservationInput = {
  startDate: Date;
  endDate: Date;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
};

function Reservation() {
  const { id } = useParams();
  const [carDetail, setCarDetail] = useState<CarDetailInfo>();

  const {
    control,
    register,
    handleSubmit,
    watch,
    setValue,
    formState: { errors },
  } = useForm<ReservationInput>({
    defaultValues: {
      startDate: new Date(),
      endDate: new Date(),
    },
  });

  const startDateWatch = watch("startDate");
  const endDateWatch = watch("endDate");
  const { toast } = useToast();
  const navigate = useNavigate();

  const onSubmit: SubmitHandler<ReservationInput> = (data) => {
    fetch(`http://localhost:8080/api/cars/${id}/reservation`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: prepareRequest(data),
    })
      .then((res) => res.json())
      .then((data) => {
        console.log(data);
        toast({
          title: "Reservation",
          description: "Your reservation has been processed successfully",
        });
        setTimeout(() => {
          navigate("/");
        }, 3000);
      });
  };

  useEffect(() => {
    fetch(`http://localhost:8080/api/cars/${id}`)
      .then((res) => res.json())
      .then((data) => setCarDetail(data));
  }, [id]);

  const calcTotalPrice = (price: Price, startDate: Date, endDate: Date) => {
    let numberOfDays: number;
    if (startDate.getTime() == endDate.getTime()) {
      numberOfDays = 1;
    } else {
      const differenceInTime = endDate.getTime() - startDate.getTime();
      numberOfDays = Math.ceil(differenceInTime / (1000 * 60 * 60 * 24)) + 1;
    }

    switch (numberOfDays) {
      case 1:
        return price.forDay;
      case 2:
      case 3:
      case 4:
        return price.forTwoToFourDays * numberOfDays;
      default:
        return price.forWeek * numberOfDays;
    }
  };

  const parseDate = (date: Date) => {
    const offset = date.getTimezoneOffset();
    const result = new Date(date.getTime() - offset * 60 * 1000);

    return result.toISOString().split("T")[0];
  };

  const prepareRequest = (data: ReservationInput) => {
    return JSON.stringify({
      ...data,
      startDate: parseDate(data.startDate),
      endDate: parseDate(data.endDate),
    });
  };

  return (
    <Container className="p-4">
      <Button asChild variant="ghost">
        <Link to={`..`} relative="path" className="flex items-center gap-1">
          <ArrowLeftIcon />
          Back to car
        </Link>
      </Button>
      <div className="sm:grid gap-5 grid-cols-1 sm:grid-cols-2 md:grid-cols-3 py-4">
        <Form.Root
          className="FormRoot md:col-start-2"
          onSubmit={handleSubmit(onSubmit)}
        >
          <div className="flex flex-col gap-3">
            <div className="flex flex-col md:flex-row gap-3">
              <Form.Field className="FormField grow basis-1/2" name="startDate">
                <Controller
                  control={control}
                  name="startDate"
                  rules={{
                    required: true,
                  }}
                  render={({ field }) => (
                    <div className="flex flex-col gap-2">
                      <Form.Label className="FormLabel">Start date</Form.Label>
                      <Popover>
                        <PopoverTrigger asChild>
                          <Form.Control asChild>
                            <Button
                              color="gray"
                              variant={"outline"}
                              className={cn(
                                "w-full pl-3 text-left font-normal"
                              )}
                            >
                              {field.value ? (
                                format(field.value, "PP")
                              ) : (
                                <span>Pick a date</span>
                              )}
                              <CalendarIcon className="ml-auto h-4 w-4 opacity-50" />
                            </Button>
                          </Form.Control>
                        </PopoverTrigger>
                        <PopoverContent className="w-auto p-0" align="start">
                          <Calendar
                            mode="single"
                            selected={field.value}
                            onSelect={(e) => {
                              if (e && endDateWatch.getDate() < e.getDate()) {
                                field.onChange(e);
                                setValue("endDate", e!);
                              } else {
                                field.onChange(e);
                              }
                            }}
                            disabled={{ before: new Date() }}
                            initialFocus
                            required
                          />
                        </PopoverContent>
                      </Popover>
                    </div>
                  )}
                />
              </Form.Field>
              <Form.Field className="FormField grow basis-1/2" name="endDate">
                <Controller
                  control={control}
                  name="endDate"
                  rules={{
                    required: true,
                  }}
                  render={({ field }) => (
                    <div className="flex flex-col gap-2">
                      <Form.Label className="FormLabel">End date</Form.Label>
                      <Popover>
                        <PopoverTrigger asChild>
                          <Form.Control asChild>
                            <Button
                              color="gray"
                              variant={"outline"}
                              className={cn(
                                "w-full pl-3 text-left font-normal"
                              )}
                            >
                              {field.value ? (
                                format(field.value, "PP")
                              ) : (
                                <span>Pick a date</span>
                              )}
                              <CalendarIcon className="ml-auto h-4 w-4 opacity-50" />
                            </Button>
                          </Form.Control>
                        </PopoverTrigger>
                        <PopoverContent className="w-auto p-0" align="start">
                          <Calendar
                            mode="single"
                            selected={field.value}
                            onSelect={field.onChange}
                            disabled={{ before: startDateWatch }}
                            initialFocus
                            required
                          />
                        </PopoverContent>
                      </Popover>
                    </div>
                  )}
                />
              </Form.Field>
            </div>
            <Form.Field className="FormField" name="firstName">
              <div className="flex justify-between items-end mb-2">
                <Form.Label className="FormLabel">First name</Form.Label>
                <Form.Message
                  className="FormMessage text-sm"
                  match="valueMissing"
                >
                  Please enter your first name
                </Form.Message>
              </div>
              <Form.Control asChild>
                <TextFieldInput
                  {...register("firstName")}
                  placeholder="John"
                  required
                />
              </Form.Control>
            </Form.Field>
            <Form.Field className="FormField" name="lastName">
              <div className="flex justify-between items-end mb-2">
                <Form.Label className="FormLabel">Last name</Form.Label>
                <Form.Message
                  className="FormMessage text-sm"
                  match="valueMissing"
                >
                  Please enter your last name
                </Form.Message>
              </div>
              <Form.Control asChild>
                <TextFieldInput
                  {...register("lastName")}
                  placeholder="Doe"
                  required
                />
              </Form.Control>
            </Form.Field>
            <Form.Field className="FormField" name="email">
              <div className="flex justify-between items-end mb-2">
                <Form.Label className="FormLabel">Email</Form.Label>
                <Form.Message
                  className="FormMessage text-sm"
                  match="valueMissing"
                >
                  Please enter your email
                </Form.Message>
                <Form.Message
                  className="FormMessage text-sm"
                  match="typeMismatch"
                >
                  Please provide a valid email
                </Form.Message>
              </div>
              <Form.Control asChild>
                <TextFieldInput
                  {...register("email")}
                  placeholder="john@doe.com"
                  type="email"
                  required
                />
              </Form.Control>
            </Form.Field>
            <Form.Field className="FormField" name="phoneNumber">
              <div className="flex justify-between items-end mb-2">
                <Form.Label className="FormLabel">Phone number</Form.Label>
                <Form.Message
                  className="FormMessage text-sm"
                  match="valueMissing"
                >
                  Please enter your phone number
                </Form.Message>
              </div>
              <Form.Control asChild>
                <TextFieldInput
                  {...register("phoneNumber")}
                  placeholder="123456789"
                  required
                />
              </Form.Control>
            </Form.Field>
            <div className="flex flex-col justify-center items-center">
              <p className="font-light text-sm">Total price</p>
              <p className="">
                {carDetail &&
                  calcTotalPrice(
                    carDetail.price,
                    startDateWatch,
                    endDateWatch
                  )}{" "}
                PLN
              </p>
            </div>
            <Form.Submit asChild>
              <Button>Reserve</Button>
            </Form.Submit>
          </div>
        </Form.Root>
      </div>
    </Container>
  );
}

export default Reservation;
