import React, { Fragment } from 'react';
import { Dialog,
         DialogActions
        } from "@material-ui/core";
import { Box, Button,} from "@material-ui/core";

import { connect } from "react-redux";
import * as actions from "../../../actions/dClientes";

function Editar(props : { imagen : string, dUsuarioToken: string, handleDialogClose : any, open : boolean, updateDCliente : any}) {
    const { imagen, handleDialogClose, open } = props;

    return (
        <Fragment>
        <Dialog open={open} onClose={handleDialogClose} aria-labelledby="form-dialog-title" maxWidth={"md"}>
            <img src={imagen}/>
            <DialogActions>
                <Box margin={1}>
                    <Button onClick={handleDialogClose} color="primary">
                    Cerrar
                    </Button>
                </Box>          
            </DialogActions>
        </Dialog>
        </Fragment>
    );
}
 
  const mapStateToProps = (state : any, props : { imagen : string, dUsuarioToken: string, handleDialogClose : any, open : boolean}) => ({
    ...props
  })
  
  const mapActionToProps = {
    updateDCliente: actions.Update
  }

export default connect(mapStateToProps, mapActionToProps)(Editar);