import React from 'react';
import { Grid, Typography } from '@material-ui/core';

function Auth(props: { children : any }) {
	const { children } = props;
	return (
        <Grid container style={{ minHeight: '100vh' }}>
        <Grid item xs={12} sm={6}>
            <img
                src={`${process.env.PUBLIC_URL}/Inn-Login.png`}
                style={{ width: '100%', height: '100%', objectFit: 'cover' }}
                alt="background"
            />
        </Grid>
        <Grid
            container
            item
            xs={12}
            sm={6}
            alignItems="center"
            direction="column"
            justify="space-between"
            style={{ padding: 10 }}
        >
            <div />
            <Grid item style={{ display: 'flex', flexDirection: 'column', maxWidth: 400, minWidth: 300 }}>
                <Grid container justify="center">
                    <img src={`${process.env.PUBLIC_URL}/Zapapp-Logo.png`} width={200} alt="logo" />
                </Grid>
				    {children}
                </Grid>
				<Grid container justify="center" spacing={2}>
					<Grid item>
						<Typography variant="caption">Copyright © 2020 - Zapapp. All rights reserved.</Typography>
					</Grid>
				</Grid>
			</Grid>
		</Grid>
	);
}
export default Auth;