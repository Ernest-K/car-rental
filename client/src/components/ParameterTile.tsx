interface ParameterTileProps {
  label: string;
  value: string | number;
  unit?: string;
}

function ParameterTile({ label, value, unit }: ParameterTileProps) {
  return (
    <div className="p-4 border rounded-md duration-100">
      <p className="capitalize text-muted-foreground">{label}</p>
      <p className="capitalize text-lg font-medium">
        {value}
        {unit ? <span className="font-normal text-sm">[{unit}]</span> : null}
      </p>
    </div>
  );
}

export default ParameterTile;
