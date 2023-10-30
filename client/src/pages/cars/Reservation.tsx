import * as Form from "@radix-ui/react-form";
import { ArrowLeftIcon } from "@radix-ui/react-icons";
import { Button, Container, TextFieldInput } from "@radix-ui/themes";
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
      <div className="grid gap-5 md:grid-cols-3 py-4">
        <Form.Root className="FormRoot">
          <div className="flex flex-col gap-3">
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
                <TextFieldInput placeholder="first name" required />
              </Form.Control>
            </Form.Field>
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
                <TextFieldInput placeholder="John" required />
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
