import { createMuiTheme } from '@material-ui/core/styles';

import brown from '@material-ui/core/colors/brown';
import green from '@material-ui/core/colors/green';

const theme = createMuiTheme({
  palette: {
    primary: {
      main: brown['500'],
    },
    secondary: {
      main: '#433937',
    },
  },
});

export default theme;