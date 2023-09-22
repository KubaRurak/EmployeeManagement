import { IconCash } from '@tabler/icons-react';
import { Avatar, Card, CardContent, Stack, SvgIcon, Typography } from '@mui/material';
import { getTotalProfit } from '../../../../api/StatisticsApiSerivce';
import React, { useState, useEffect } from 'react';
import BlankCard from '../../../components/shared/BlankCard';

const TotalProfitCard = () => {

  const [totalProfit, setTotalProfit] = useState(0);


  useEffect(() => {
    getTotalProfit().then(response => {
      setTotalProfit(response.data);
    }).catch(error => {
      console.error("Error fetching work order numbers:", error);
    });
  }, []);
  const formatToK = (num) => {
    if (num >= 1000000) {
      return (num / 1000000).toFixed(3) + 'm';
    }
    return num.toString();
  };

  const formattedTotalProfit = formatToK(totalProfit);

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
              TOTAL PROFIT
            </Typography>
            <Typography variant="h3">
              {formattedTotalProfit} zł
            </Typography>
          </Stack>
          <Avatar
            sx={{
              backgroundColor: 'primary.main',
              height: 56,
              width: 56
            }}
          >
            <SvgIcon>
              <IconCash />
            </SvgIcon>
          </Avatar>
        </Stack>
      </CardContent>
    </BlankCard>
  );
};

export default TotalProfitCard;


