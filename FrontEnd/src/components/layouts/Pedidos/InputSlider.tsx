import React, { useState } from 'react';
import Grid from '@material-ui/core/Grid';
import Slider from '@material-ui/core/Slider';
import Input from '@material-ui/core/Input';

function InputSlider(props: {isSelected : boolean, initialValue: number, setListCostos: any, listCostos : number[], costoIndex: number, min: number, max: number}) {
  const { isSelected, initialValue, setListCostos,min,max, costoIndex, listCostos} = props;
  const [value, setValue] = useState<number | string | Array<number | string>>(initialValue);

  const handleSliderChange = (event: any, newValue: number | number[]) => {
    if( typeof newValue === 'number'){
      listCostos[costoIndex] = newValue;
      setListCostos(listCostos);
    }
    setValue(newValue);
  };

  return (
    <div>
      <Grid container alignItems="center">
        <Grid item xs>
          <Slider
            value={typeof value === 'number' ? value : 0}
            onChange={handleSliderChange}
            aria-labelledby="input-slider"
            disabled={isSelected}
            step={1}
            min= {min}
            max= {max}
          />
        </Grid>
        <Grid item>
          <Input
            value={value}
            margin="dense"
            inputProps={{
              step: 10,
              min: min,
              max: max,
              type: 'number',
              'aria-labelledby': 'input-slider',
            }}
            disabled={true}
          />
        </Grid>
      </Grid>
    </div>
  );
}
export default InputSlider;