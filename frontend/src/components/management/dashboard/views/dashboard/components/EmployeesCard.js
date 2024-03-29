import { IconUsers } from '@tabler/icons-react';
import { Avatar, Card, CardContent, Stack, SvgIcon, Typography } from '@mui/material';
import { retrieveEmployeeAmount } from '../../../../api/UserApiService';
import React, { useState, useEffect } from 'react';
import BlankCard from '../../../components/shared/BlankCard';

const EmployeesCard = () => {

  const [amountEmployees, setAmountEmployees] = useState(0);

  useEffect(() => {
    retrieveEmployeeAmount().then(response => {
      setAmountEmployees(response.data);
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
              TOTAL EMPLOYEES
            </Typography>
            <Typography variant="h3">
              {amountEmployees}
            </Typography>
          </Stack>
          <Avatar
            sx={{
              backgroundColor: '#f44336',
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
    </BlankCard>
  );
};

export default EmployeesCard;


