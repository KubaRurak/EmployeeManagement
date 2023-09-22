import { IconChecklist } from '@tabler/icons-react';
import { Avatar, Card, CardContent, Stack, SvgIcon, Typography } from '@mui/material';
import { retrieveWorkOrderNumbers } from '../../../../api/WorkOrdersApiService';
import React, { useState, useEffect } from 'react';
import BlankCard from '../../../components/shared/BlankCard';

const WorkOrdersCard = () => {

  const [amountWorkorders, setAmountWorkorders] = useState(0);

  useEffect(() => {
    retrieveWorkOrderNumbers().then(response => {
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
              TOTAL WORK ORDERS
            </Typography>
            <Typography variant="h3">
              {amountWorkorders}
            </Typography>
          </Stack>
          <Avatar
            sx={{
              backgroundColor: 'success.main',
              height: 56,
              width: 56
            }}
          >
            <SvgIcon>
              <IconChecklist />
            </SvgIcon>
          </Avatar>
        </Stack>
      </CardContent>
    </BlankCard>
  );
};

export default WorkOrdersCard;


