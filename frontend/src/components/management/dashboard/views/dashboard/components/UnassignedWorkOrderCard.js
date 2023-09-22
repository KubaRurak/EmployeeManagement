import { IconFileAlert } from '@tabler/icons-react';
import { Avatar, CardContent, Stack, SvgIcon, Typography } from '@mui/material';
import { retrieveWorkOrderNumbers } from '../../../../api/WorkOrdersApiService';
import React, { useState, useEffect } from 'react';
import BlankCard from '../../../components/shared/BlankCard';

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
    <BlankCard>
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
              backgroundColor: '#ffc107',
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
    </BlankCard>
  );
};

export default UnassignedWorkOrdersCard;


