import { IconFileAlert } from '@tabler/icons-react';
import { Avatar, Card, CardContent, Stack, SvgIcon, Typography } from '@mui/material';
import { retrieveWorkOrderNumbers } from '../../../../api/WorkOrdersApiService';
import React, { useState, useEffect } from 'react';

const UnassignedWorkOrdersCard = () => {

  const [amountWorkorders, setAmountWorkorders] = useState(0);

  useEffect(() => {
    retrieveWorkOrderNumbers('UNASSIGNED').then(response => {
        setAmountWorkorders(response.data);
    }).catch(error => {
        console.error("Error fetching work order numbers:", error);
    });
}, []);

  return (
    <Card>
      <CardContent>
        <Stack
          alignItems="flex-start"
          direction="row"
          justifyContent="space-between"
          spacing={3}
        >
          <Stack spacing={1}>
            <Typography
              color="text.secondary"
              variant="overline"
            >
              UNASSIGNED WORK ORDERS
            </Typography>
            <Typography variant="h3">
              {amountWorkorders}
            </Typography>
          </Stack>
          <Avatar
            sx={{
              backgroundColor: 'secondary.main',
              height: 56,
              width: 56
            }}
          >
            <SvgIcon>
              <IconFileAlert />
            </SvgIcon>
          </Avatar>
        </Stack>
      </CardContent>
    </Card>
  );
};

export default UnassignedWorkOrdersCard;


