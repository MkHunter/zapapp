import React, { useState } from 'react';
import clsx from 'clsx';
import { makeStyles, useTheme, Theme, createStyles } from '@material-ui/core/styles';
import Drawer from '@material-ui/core/Drawer';
import CssBaseline from '@material-ui/core/CssBaseline';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import List from '@material-ui/core/List';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';
import IconButton from '@material-ui/core/IconButton';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Paper from "@material-ui/core/Paper";

import { ChevronLeft as ChevronLeftIcon, ChevronRight as ChevronRightIcon,
    MoveToInbox as InboxIcon,
    Home as HomeIcon,
    ListAlt as CatalogoIcon,
    BarChart as ReportsIcon,
    Group as ClientesIcon,
    Brush as ServicesIcon,
    Forum as ConversacionesIcon,
    AccountCircle, 
    Menu as MenuIcon } from "@material-ui/icons";
import { useHistory } from "react-router-dom";
import { Badge, Grid, Menu, MenuItem } from '@material-ui/core';

import { connect } from 'react-redux';
import * as actions from "../../../actions/dUsuario";

const drawerWidth = 240;

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      display: 'flex',
    },
    appBar: {
      transition: theme.transitions.create(['margin', 'width'], {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
      }),
    },
    appBarShift: {
      width: `calc(100% - ${drawerWidth}px)`,
      marginLeft: drawerWidth,
      transition: theme.transitions.create(['margin', 'width'], {
        easing: theme.transitions.easing.easeOut,
        duration: theme.transitions.duration.enteringScreen,
      }),
    },
    menuButton: {
      marginRight: theme.spacing(2),
    },
    hide: {
      display: 'none',
    },
    drawer: {
      width: drawerWidth,
      flexShrink: 0,
    },
    drawerPaper: {
      width: drawerWidth,
    },
    drawerHeader: {
      display: 'flex',
      alignItems: 'center',
      padding: theme.spacing(0, 1),
      // necessary for content to be below app bar
      ...theme.mixins.toolbar,
      justifyContent: 'flex-end',
    },
    content: {
      flexGrow: 1,
      padding: theme.spacing(3),
      transition: theme.transitions.create('margin', {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
      }),
      marginLeft: -drawerWidth,
    },
    contentShift: {
      transition: theme.transitions.create('margin', {
        easing: theme.transitions.easing.easeOut,
        duration: theme.transitions.duration.enteringScreen,
      }),
      marginLeft: 0,
    },
    grow: {
      flexGrow: 1,
    },
    title: {
      display: 'block',
    },
    sectionDesktop: {
      display: 'flex'
    }
  }),
);

function Home(props : { children? : any, logoutDUsuario?: any, dUserName?: string}) {
  const { children, logoutDUsuario, dUserName } = props;
  const history = useHistory();
  const classes = useStyles();
  const theme = useTheme();
  const [open, setOpen] = useState(true);

  const handleDrawerOpen = () => {
    setOpen(true);
  };

  const handleDrawerClose = () => {
    setOpen(false);
  };

  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);

  const isMenuOpen = Boolean(anchorEl);

  const handleProfileMenuOpen = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
  };
  const itemsList : { text: string, onClick : () => void}[] = [
    {
      text: "Perfil",
      onClick: () => {
        handleMenuClose();
        history.push("/perfil")
      }
    },
    {
      text: "Cerrar Sesion",
      onClick: () => {
        logoutDUsuario(() => history.push("/login"));
      }
    }
  ];

  const menuId : string = 'primary-search-account-menu';
  const renderMenu = (
    <Menu
      anchorEl={anchorEl}
      anchorOrigin={{ vertical: 'top', horizontal: 'left' }}
      id={menuId}
      keepMounted
      transformOrigin={{ vertical: 'top', horizontal: 'right' }}
      open={isMenuOpen}
      onClose={handleMenuClose}
    >
      {itemsList.map((item) => {
            const { text, onClick } = item;
            return (
              <MenuItem key={item.text} onClick={onClick}>
                {text}
              </MenuItem>
            );
          }
        )}
    </Menu>
  );

  const menuListItems = [
    {
      text: "Inicio",
      icon: <HomeIcon color="primary"/>
    },
    {
      text: "Pedidos",
      icon: <InboxIcon color="primary"/>
    },
    {
      text: "Clientes",
      icon: <ClientesIcon color="primary"/>
    },
    {
      text: "Servicios",
      icon: <ServicesIcon color="primary"/>
    },
    {
      text: "Reportes",
      icon: <ReportsIcon color="primary"/>
    },
    {
      text: "Conversaciones",
      icon: <ConversacionesIcon color="primary"/>
    },
    {
      text: "Catálogo",
      icon: <CatalogoIcon color="primary"/>
    }
  ];
  return (
    <div className={classes.root}>
      <CssBaseline />
      <AppBar
        position="fixed"
        className={clsx(classes.appBar, {
          [classes.appBarShift]: open,
        })}
      >
        <Toolbar>
          <IconButton
            color="inherit"
            onClick={handleDrawerOpen}
            className={clsx(classes.menuButton, 
              open && classes.hide)}
          >
            <MenuIcon />
          </IconButton>
          <Typography className={classes.title} variant="h6" noWrap>
            
          </Typography>
          <div className={classes.grow} />
          <div className={classes.sectionDesktop}>
            <Grid
              container
              direction="row"
              justify="center"
              alignItems="center"
            >
            <Typography>
              {dUserName}
            </Typography>
            </Grid>
            <IconButton
              edge="end"
              aria-controls={menuId}
              onClick={handleProfileMenuOpen}
              color="inherit"
            >
              <AccountCircle/>
            </IconButton>
          </div>
        </Toolbar>
      </AppBar>
      {renderMenu}
      <Drawer
        className={classes.drawer}
        variant="persistent"
        anchor="left"
        open={open}
        classes={{
          paper: classes.drawerPaper,
        }}
      >
        <div className={classes.drawerHeader}>
          <IconButton onClick={handleDrawerClose}>
            {theme.direction === 'ltr' ? <ChevronLeftIcon /> : <ChevronRightIcon />}
          </IconButton>
        </div>
        <Divider />
        <List>
          {menuListItems.map((menuItem) => {
              const { text, icon } = menuItem;
              return(
                <ListItem key={text} button onClick={() => history.push("/"+text.toLowerCase().replace(/á/g,'a'))}>
                    <ListItemIcon>{icon}</ListItemIcon>
                    <ListItemText primary={text} />
                </ListItem>
            );})
          }
        </List>
      </Drawer>
      <main
        className={clsx(classes.content, {
          [classes.contentShift]: open,
        })}
      >
        <div className={classes.drawerHeader} />
        <Paper>
            {children}
        </Paper>
      </main>
    </div>
  );
}
/*  //TODO: Boton Notificaciones
            <IconButton color="inherit">
              <Badge badgeContent={11} color="secondary">
                <MailIcon />
              </Badge>
            </IconButton>
            <IconButton color="inherit">
              <Badge badgeContent={6} color="secondary">
                <NotificationsIcon />
              </Badge>
            </IconButton>
*/

const mapStateToProps = (state : any) => ({
  dUserName: state.dUsuario.userData.nombreUsuario
})

const mapActionToProps = {
  logoutDUsuario: actions.Logout
}

export default connect(mapStateToProps,mapActionToProps)(Home);