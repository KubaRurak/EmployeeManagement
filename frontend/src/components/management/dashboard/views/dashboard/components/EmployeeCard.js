import { IconUsers } from '@tabler/icons-react';
import { Avatar, Card, CardContent, Stack, SvgIcon, Typography } from '@mui/material';
import { retrieveEmployeeAmount } from '../../../../api/UserApiService';
import React, { useState, useEffect } from 'react';

const UnassignedEmployeesCard = () => {

  const [amountEmployees, setAmountEmployees] = useState(0);

  useEffect(() => {
    retrieveEmployeeAmount('UNASSIGNED').then(response => {
        console.log(response.data);
        setAmountEmployees(response.data);
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
              {amountEmployees}
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
              <IconUsers />
            </SvgIcon>
          </Avatar>
        </Stack>
      </CardContent>
    </Card>
  );
};

export default UnassignedEmployeesCard;


