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
import { useState } from "react";
import { Link, useParams } from "react-router-dom";
import { useForm, SubmitHandler } from "react-hook-form";

type Inputs = {
  startDate: Date;
  endDate: Date;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
};

function Reservation() {
  const { id } = useParams();
  const [startDate, setStartDate] = useState<Date>();
  const [endDate, setEndDate] = useState<Date>();

  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
  } = useForm<Inputs>();

  const onSubmit: SubmitHandler<Inputs> = (data) => console.log(data);

  return (
    <Container className="p-4">
      <Button asChild variant="ghost">
        <Link to={`..`} relative="path" className="flex items-center gap-1">
          <ArrowLeftIcon />
          Back to car
        </Link>
      </Button>
      <div className="grid gap-5 sm:grid-cols-2 md:grid-cols-3 py-4">
        <Form.Root className="FormRoot" onSubmit={handleSubmit(onSubmit)}>
          <div className="flex flex-col gap-3">
            <div className="flex flex-col md:flex-row gap-3">
              <Form.Field className="FormField grow basis-1/2" name="startDate">
                <div className="flex justify-between items-end mb-2">
                  <Form.Label className="FormLabel">Start date</Form.Label>
                  <Form.Message
                    className="FormMessage text-sm"
                    match="valueMissing"
                  >
                    Please enter start date
                  </Form.Message>
                </div>
                <Popover>
                  <PopoverTrigger asChild>
                    <Form.Control asChild>
                      <Button
                        color="gray"
                        variant={"outline"}
                        className={cn("w-full pl-3 text-left font-normal")}
                      >
                        {startDate ? (
                          format(startDate, "PP")
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
                      selected={startDate}
                      onSelect={setStartDate}
                      disabled={(date) =>
                        date > new Date() || date < new Date("1900-01-01")
                      }
                      initialFocus
                      required
                    />
                  </PopoverContent>
                </Popover>
              </Form.Field>
              <Form.Field className="FormField grow basis-1/2" name="endDate">
                <div className="flex justify-between items-end mb-2">
                  <Form.Label className="FormLabel">End date</Form.Label>
                  <Form.Message
                    className="FormMessage text-sm"
                    match="valueMissing"
                  >
                    Please enter end date
                  </Form.Message>
                </div>
                <Popover>
                  <PopoverTrigger asChild>
                    <Form.Control asChild>
                      <Button
                        color="gray"
                        variant={"outline"}
                        className={cn("w-full pl-3 text-left font-normal")}
                      >
                        {endDate ? (
                          format(endDate, "PP")
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
                      selected={endDate}
                      onSelect={setEndDate}
                      disabled={(date) =>
                        date > new Date() || date < new Date("1900-01-01")
                      }
                      initialFocus
                      required
                    />
                  </PopoverContent>
                </Popover>
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
                <TextFieldInput placeholder="Doe" required />
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
                <TextFieldInput placeholder="123456789" required />
              </Form.Control>
            </Form.Field>
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
